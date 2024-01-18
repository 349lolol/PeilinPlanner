const grid = document.getElementById('grid');
const gridBounds = grid.getBoundingClientRect();
let scale = 1;


// const throttle = (func, interval) => {
//   let inThrottle = false;

//   return () => {
//     if (!inThrottle) {
//       func(...args);
//       inThrottle = true;

//       setTimeout(() => {
//         inThrottle = false;
//       }, interval);
//     }
//   }
// }

 // ZOOMING

const zoom = (e, factor) => {
  for (let child of grid.children) {
    const width = child.offsetWidth;
    const height = child.offsetHeight;

    const clientX = e.clientX - grid.offsetLeft;
    const clientY = e.clientY - grid.offsetTop;

    let x1 = 0;
    let y1 = 0;
    if (!(child.style.transform === "")) {
      const position = child.style.transform.slice(10, -1); // __px, __px)
      x1 = Number(position.split(", ")[0].slice(0, -2)) // px
      y1 = Number(position.split(", ")[1].slice(0, -2)) // px
    }

    const x2 = x1 + width;
    const y2 = y1;

    const m1 = -(y1 - clientY)/(x1 - clientX);
    const m2 = -(y2 - clientY)/(x2 - clientX);

    const b1 = y1 + m1*x1;
    const b2 = -(y2 + m2*x2)

    const xDisplacement1 = x1;

    if (e.deltaY < 0) {
      scale *= factor
      child.style.scale = scale

      const newXPosition = (factor*((m2*xDisplacement1) - (m1*xDisplacement1) - b1 + b2) + b1 - b2)/(m2 - m1) - xDisplacement1;
      const newYPosition = m1*newXPosition + b1;
      const xTranslation = newXPosition - xDisplacement1;
      const yTranslation = newYPosition - y1;

      child.style.transform = `translate(${ x1 + xTranslation}px, ${y1 + yTranslation}px)`
      console.log(m1, m2, xDisplacement1, b1, b2, clientX, clientY, newXPosition, newYPosition)
      console.log(newXPosition, xDisplacement1, newYPosition, y1, xDisplacement1)
      
    } else {
      scale /= factor
      child.style.scale = scale

      const newXPosition = (((m2*xDisplacement1) - (m1*xDisplacement1) - b1 + b2) + b1 - b2)/(factor*(m2 - m1)) - xDisplacement1;
      const newYPosition = m1*newXPosition + b1;

      const xTranslation = newXPosition - xDisplacement1;
      const yTranslation = newYPosition - y1;

      child.style.transform = `translate(${x1 + xTranslation}px, ${y1 + yTranslation}px)`
      console.log(m1, m2, xDisplacement1, b1, b2, clientX, clientY, newXPosition, newYPosition)
      console.log(newXPosition, xDisplacement1, newYPosition, y1, xDisplacement1)
    }

    

  }
}


grid.addEventListener("wheel", (e) => {
  zoom(e, 2)
})


// SNAPPING

const elements = document.querySelectorAll('.diagram');

elements.forEach((element) => {
  let x = 0;
  let y = 0

  interact(element)
    .draggable({
      modifiers: [
        interact.modifiers.snap({
          targets: [
            interact.snappers.grid({
              x: 10,
              y: 10, 
            })
          ],
          range: Infinity,
          relativePoints: [ { x: gridBounds.width / 2 + gridBounds.x, y: gridBounds.height / 2 + gridBounds.y } ]
        }),
        interact.modifiers.restrict({
          restriction: element.parentNode,
          elementRect: { top: 0, left: 0, bottom: 1, right: 1 },
          endOnly: true
        })
      ],
      inertia: {
        resistance: 100000,
      }
    })
    .on('dragmove', function (event) {
      console.log(event)
      x += event.dx / scale
      y += event.dy / scale

      event.target.style.transform = 'translate(' + x + 'px, ' + y + 'px)'
    })
})


// ARROWS
// FUNCTIONS FOR ARROWS
const lineDraw = (ax, ay, bx, by, type) => {
  if(ax > bx) {
      bx = ax + bx; 
      ax = bx - ax;
      bx = bx - ax;

      by = ay + by;
      ay = by - ay;
      by = by - ay;
  }

  const distance = Math.sqrt(Math.pow(bx - ax, 2) + Math.pow(by - ay, 2));
  const calc = Math.atan((by - ay) / (bx - ax));
  const degree = calc * 180 / Math.PI;

  const line = document.createElement('div');

  if (type === "SOLID") {
    line.style.cssText = `
        position: absolute;
        height: 2px;
        transform-origin: top left;
        width: ${distance}px;
        top: ${ay}px;
        left: ${ax}px;
        transform: rotate(${degree}deg);
        background-color: black;
    `;
  } else if (type === "DOTTED") {
    line.style.cssText = `
    position: absolute;
    height: 2px;
    transform-origin: top left;
    width: ${distance}px;
    top: ${ay}px;
    left: ${ax}px;
    transform: rotate(${degree}deg);
    border: 1px dotted black;
    `;
  }


  document.querySelector("#arrows").appendChild(line);
}

const pointDraw = (x, y) => {
  const dot = document.createElement("div")
  const radius = 4

  dot.style.cssText = `
    height: ${2*radius}px;
    width: ${2*radius}px;
    top: ${y - radius}px;
    left: ${x - radius}px;
    background-color: black;
    border-radius: 50%;
    display: inline-block;
    position: absolute;
  }`

  document.querySelector("#arrows").appendChild(dot);
};

const headDraw = (ax, ay, bx, by) => {


  const triangle = document.createElement("div")

  let degree = 0;

  if (ax <= bx) {
    const calc = Math.atan((by - ay) / (bx - ax));
    degree = 90 + (calc * 180 / Math.PI);
  }
  else {
    const calc = Math.atan((by - ay) / (bx - ax));
    degree = -(180 - (90 + (calc * 180 / Math.PI)));
  }

  console.log(degree)

  triangle.style.cssText = `
    width: 0;
    height: 0;
    position: absolute;
    top: ${by - 10}px;
    left: ${bx - 10}px;
    border: solid 10px;
    border-color: transparent transparent black transparent;
    transform: rotate(${degree}deg);
  }`

  document.querySelector("#arrows").appendChild(triangle);
};

// DRAWING ARROWS

// const arrows = axios.get("URL");
// const arrowData = arrows.data;

const arrowData = {
  arrow: {
    xPoints: [0, 200, 300, 400, 500],
    yPoints: [0, 300, 100, 600, 700]
  }
}

for (let arrow in arrowData) {
  for (let i = 0; i < arrowData[arrow].xPoints.length - 1; i++) {
    let x1 = arrowData[arrow].xPoints[i];
    let y1 = arrowData[arrow].yPoints[i];

    let x2 = arrowData[arrow].xPoints[i + 1];
    let y2 = arrowData[arrow].yPoints[i + 1];

    lineDraw(x1, y1, x2, y2, "SOLID")
    pointDraw(x1, y1)

    if (i === arrowData[arrow].xPoints.length - 2) {
      headDraw(x1, y1, x2, y2)
    }
  }

}

let pointIndex = null;
let arrowID = null;
let end = null;
let move = null;
let dragging = false;


grid.addEventListener('mousedown', (e) => {
let x = e.x - grid.offsetLeft;
let y = e.y - grid.offsetTop;

console.log(x, y)

  for (let arrow in arrowData) {
    for (let i = 0; i < arrowData[arrow].xPoints.length ; i++) {
      let xPoint = arrowData[arrow].xPoints[i];
      let yPoint = arrowData[arrow].yPoints[i];

      if (Math.sqrt((xPoint - x)**2 + (yPoint - y)**2) <= 20) {
        pointIndex = i;
        dragging = true;
        arrowID = arrow;
      }
    }
  }
})

// POINT DRAGGING
grid.addEventListener('mouseup', (e) => {
  dragging = false;
  pointIndex = null;
  arrowID = 0;
})

grid.addEventListener("mousemove", (e) => {
  if (dragging) {

    arrowData[arrowID].xPoints[pointIndex] = e.x - grid.offsetLeft;
    arrowData[arrowID].yPoints[pointIndex] = e.y - grid.offsetTop;

    document.querySelector("#arrows").innerHTML = "";

    for (let arrow in arrowData) {
      for (let i = 0; i < arrowData[arrow].xPoints.length - 1; i++) {
        let x1 = arrowData[arrow].xPoints[i];
        let y1 = arrowData[arrow].yPoints[i];
    
        let x2 = arrowData[arrow].xPoints[i + 1];
        let y2 = arrowData[arrow].yPoints[i + 1];
    
        lineDraw(x1, y1, x2, y2, "SOLID")
        pointDraw(x1, y1)

        if (i === arrowData[arrow].xPoints.length - 2) {
          headDraw(x1, y1, x2, y2)
        }
      }
    }
  }
})

// ARROW SNAPPING (NOT IMPLEMENTED)

// const arrows = document.querySelectorAll('#arrows > div');

// arrows.forEach((arrow) => {
//   let x = 0;
//   let y = 0;

//   console.log("hi")

//   interact(arrow)
//     .draggable({
//       modifiers: [
//         interact.modifiers.snap({
//           targets: [
//             interact.snappers.grid({
//               x: 10,
//               y: 10, 
//             })
//           ],
//           range: Infinity,
//           relativePoints: [ { x: gridBounds.width / 2 + gridBounds.x, y: gridBounds.height / 2 + gridBounds.y } ]
//         }),
//         interact.modifiers.restrict({
//           restriction: arrow.parentNode,
//           elementRect: { top: 0, left: 0, bottom: 1, right: 1 },
//           endOnly: true
//         })
//       ],
//       inertia: {
//         resistance: 100000,
//       }
//     })
//     .on('dragmove', function (event) {
//       console.log(event)
//       x += event.dx / scale
//       y += event.dy / scale

//       event.target.style.transform = 'translate(' + x + 'px, ' + y + 'px)'
//     })
// })

// move points
// send updated points back