const grid = document.getElementById('grid');
const gridBounds = grid.getBoundingClientRect();
let scale = 1;
let defaultDiagramWidth = 10;
let defaultLineHeight = 0.8;

const snap = () => {
  const elements = document.querySelectorAll('.diagram');
console.dir("beans" + elements);

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
}

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
    let y1 = grid.offsetHeight;
    if (!(child.style.transform === "")) {
      const position = child.style.transform.slice(10, -1); // __px, __px)
      x1 = Number(position.split(", ")[0].slice(0, -2)) // px
      y1 = grid.offsetHeight - Number(position.split(", ")[1].slice(0, -2)) // px
    }

    const x2 = x1 + width;
    const y2 = y1;

    const m1 = (y1 - clientY)/(x1 - clientX);
    const m2 = (y2 - clientY)/(x2 - clientX);

    const b1 = y1 - m1*x1;
    const b2 = y2 - m2*x2;

    const xDisplacement1 = clientX - x1;

    if (e.deltaY < 0) {
      scale *= factor
      child.style.scale = scale

      const newXPosition = (factor*((m2*xDisplacement1) - (m1*xDisplacement1) - b1 + b2) + b1 - b2)/(m2 - m1);
      console.log(newXPosition);
      const newYPosition = m1*newXPosition + b1;
      const xTranslation = newXPosition - x1;
      const yTranslation = newYPosition - y1;

      child.style.transform = `translate(${ x1 + xTranslation}px, ${y1 - yTranslation}px)`;
      
    } else {
      scale /= factor
      child.style.scale = scale

      const newXPosition = (((m2*xDisplacement1) - (m1*xDisplacement1) - b1 + b2) + b1 - b2)/(factor*(m2 - m1));
      const newYPosition = m1*newXPosition + b1;

      const xTranslation = newXPosition - xDisplacement1;
      const yTranslation = newYPosition - y1;

      child.style.transform = `translate(${x1 + xTranslation}px, ${y1 - yTranslation}px)`;
    }
  }
}

grid.addEventListener("wheel", (e) => {
  zoom(e, 2)


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

grid.addEventListener('mouseup', (e) => {

  // const data = {
  //   arrow: arrowID,
  //   arrowPointIndex: pointIndex,
  //   newX: arrowData[arrowID].xPoints[pointIndex],
  //   newY: arrowData[arrowID].yPoints[pointIndex],
  // }

  // axios.post("SERVERURL", data)
  // .then(res => {

  // })
  // .catch(err => {
  //   console.log("ERROR SAVING DATA")
  // })


  dragging = false;
  pointIndex = null;
  arrowID = 0;
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

// LOAD IN DIAGRAMS

// ADDING NEW DIAGRAMS


let diagramNum = 0;

const addDiagram = (x, y, diagramWidth, type) => {
  diagramNum++;
  const diagram = document.createElement("div");
  diagram.id = "diagram" + diagramNum;
  diagram.classList.add("diagram");

  diagram.style.cssText = `
    width: ${diagramWidth}rem;
    display: flex;
    flex-direction: column;
    background-color: rgb(230, 230, 230);
    border: 2px solid black;
    position: absolute;

    scale: 1;
    z-index: 2;
  }`

  if (type === "CLASSDIAGRAM") {
    diagram.classList.add("classdiagram");
    const name = document.createElement("textarea");
    name.style.cssText = `
      border-top: 4px solid #ff9f2a;
      resize: none;
      font-family: "Montserrat", sans-serif;
      font-optical-sizing: auto;
      font-weight: 500;
      font-style: normal;
      font-size: 0.8rem;
      border-bottom: 1px solid black;
      text-align: center;
    `

    name.name = "name";
    name.cols = "20";
    name.rows = "1";
    name.placeholder = "name";
    name.classList.add("name")

    const fields = document.createElement("textarea");
    fields.style.cssText = `
      resize: none;
      font-family: "Montserrat", sans-serif;
      font-optical-sizing: auto;
      font-weight: 500;
      font-style: normal;
      font-size: 0.8rem;`

    fields.name = "fields";
    fields.cols = "20";
    fields.rows = "5";
    fields.placeholder = "fields";
    fields.classList.add("fields")
    
    const methods = document.createElement("textarea");
    methods.style.cssText = `
      resize: none;
      font-family: "Montserrat", sans-serif;
      font-optical-sizing: auto;
      font-weight: 500;
      font-style: normal;
      font-size: 0.8rem;
      border-top: 1px solid black;`

    methods.name = "methods";
    methods.cols = "20";
    methods.rows = "5";
    methods.placeholder = "methods";
    methods.classList.add("methods")

    diagram.appendChild(name);
    diagram.appendChild(fields);
    diagram.appendChild(methods);

  } else if (type === "INTERFACEDIAGRAM") {
    diagram.classList.add("interfacediagram");
    const name = document.createElement("textarea");
    name.style.cssText = `
      border-top: 4px solid #2aff66;
      resize: none;
      font-family: "Montserrat", sans-serif;
      font-optical-sizing: auto;
      font-weight: 500;
      font-style: normal;
      font-size: 0.8rem;
      border-bottom: 1px solid black;
      text-align: center;
    `

    name.name = "name";
    name.cols = "20";
    name.rows = "1";
    name.placeholder = "name";
    name.classList.add("name")
    
    const methods = document.createElement("textarea");
    methods.style.cssText = `
      resize: none;
      font-family: "Montserrat", sans-serif;
      font-optical-sizing: auto;
      font-weight: 500;
      font-style: normal;
      font-size: 0.8rem;
      border-top: 1px solid black;`

    methods.name = "methods";
    methods.cols = "20";
    methods.rows = "5";
    methods.placeholder = "methods";
    methods.classList.add("methods")

    diagram.appendChild(name);
    diagram.appendChild(methods);

  } else if (type === "ABSTRACTCLASSDIAGRAM") {
    diagram.classList.add("abstractclassdiagram");
    const name = document.createElement("textarea");
    name.style.cssText = `
      border-top: 4px solid #2ebdfc;
      resize: none;
      font-family: "Montserrat", sans-serif;
      font-optical-sizing: auto;
      font-weight: 500;
      font-style: normal;
      font-size: 0.8rem;
      border-bottom: 1px solid black;
      text-align: center;
    `

    name.name = "name";
    name.cols = "20";
    name.rows = "1";
    name.placeholder = "name";
    name.classList.add("name")

    const fields = document.createElement("textarea");
    fields.style.cssText = `
      resize: none;
      font-family: "Montserrat", sans-serif;
      font-optical-sizing: auto;
      font-weight: 500;
      font-style: normal;
      font-size: 0.8rem;`

    fields.name = "fields";
    fields.cols = "20";
    fields.rows = "5";
    fields.placeholder = "fields";
    fields.classList.add("fields")
    
    const methods = document.createElement("textarea");
    methods.style.cssText = `
      resize: none;
      font-family: "Montserrat", sans-serif;
      font-optical-sizing: auto;
      font-weight: 500;
      font-style: normal;
      font-size: 0.8rem;
      border-top: 1px solid black;`

    methods.name = "methods";
    methods.cols = "20";
    methods.rows = "5";
    methods.placeholder = "methods";
    methods.classList.add("methods")

    diagram.appendChild(name);
    diagram.appendChild(fields);
    diagram.appendChild(methods);

  } else if (type === "EXCEPTIONDIAGRAM") {
    diagram.classList.add("exceptiondiagram");
    const name = document.createElement("textarea");
    name.style.cssText = `
      border-top: 4px solid #ff2626;
      resize: none;
      font-family: "Montserrat", sans-serif;
      font-optical-sizing: auto;
      font-weight: 500;
      font-style: normal;
      font-size: 0.8rem;
      border-bottom: 1px solid black;
      text-align: center;
    `

    name.name = "name";
    name.cols = "20";
    name.rows = "1";
    name.placeholder = "name";
    name.classList.add("name")

    diagram.append(name)
  }

  grid.appendChild(diagram);

  console.dir(diagram)
}

addDiagram(500, 500, "CLASS")
addDiagram(700, 800, "EXCEPTION")

const diagrams = axios.get("SERVERURL")
const diagramsData = diagrams.data;

diagrams = {
  data: {
    
  }
}

// for (let diagram in diagramsData) {
//   // need to add diagram.ySize in relation to the lines
//   addDiagram(diagram.xPosition, diagram.yPosition, diagram.xSize, diagram.objecttype)
// }
document.querySelector("#addclass > img").addEventListener("click", (e) => {
  addDiagram((grid.offsetWidth - (defaultDiagramWidth*scale)/2) + grid.offsetLeft,
   ((grid.offsetHeight - defaultLineHeight*11*scale)/2) + grid.offsetTop, defaultDiagramWidth*scale,
   "CLASSDIAGRAM")
  
   console.dir(diagramsData);
   snap();
})
document.querySelector("#addinterface > img").addEventListener("click", (e) => {
  addDiagram((grid.offsetWidth - (defaultDiagramWidth*scale)/2) + grid.offsetLeft,
  ((grid.offsetHeight -defaultLineHeight*6*scale)/2) + grid.offsetTop, defaultDiagramWidth*scale,
  "INTERFACEDIAGRAM")
  snap();
})
document.querySelector("#addabstractclass > img").addEventListener("click", (e) => {
  addDiagram((grid.offsetWidth - (defaultDiagramWidth*scale)/2) + grid.offsetLeft,
  ((grid.offsetHeight - defaultLineHeight*11*scale)/2) + grid.offsetTop, defaultDiagramWidth*scale,
  "ABSTRACTCLASSDIAGRAM")
  snap();
})
document.querySelector("#addexception > img").addEventListener("click", (e) => {
  addDiagram((grid.offsetWidth - (defaultDiagramWidth*scale)/2) + grid.offsetLeft,
  ((grid.offsetHeight - defaultLineHeight*1*scale)/2) + grid.offsetTop, defaultDiagramWidth*scale,
  "EXCEPTIONDIAGRAM")
  snap();
})

snap();

// SNAPPING
