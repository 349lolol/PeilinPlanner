const grid = document.getElementById('grid');
const gridBounds = grid.getBoundingClientRect();
let scale = 1;
let defaultDiagramWidth = 164;
let defaultLineHeight = 16;

let diagrams = axios.get("SERVERURL")
const diagramsData = diagrams.data;

let diagram = {
  external: {
    type: "class",
    xPosition: 750,
    yPosition: 250,
    width: 164,
    height: 190,
  }
}

diagrams = {
  data: [
    {
      external: {
        type: null,
        xPosition: null,
        yPosition: null,
        width: null,
        height: null,
      },
      internal: {

      }
    },

  ]
}
// DIAGRAM SNAPPING

const snap = () => {
  const elements = document.querySelectorAll('.diagram');

  elements.forEach((element) => {
  let x = 0;
  let y = 0;

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
      x += event.dx / scale;
      y += event.dy / scale;

      event.target.style.transform = 'translate(' + x + 'px, ' + y + 'px)'

      diagrams.data[Number(element.id.slice(7))].external.xPosition = x + (grid.offsetWidth - diagrams.data[Number(element.id.slice(7))].external.width)/2;
      diagrams.data[Number(element.id.slice(7))].external.yPosition = y + (grid.offsetHeight - diagrams.data[Number(element.id.slice(7))].external.height)/2;
    })
  })
}

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

// LOAD IN DIAGRAMS

// ADDING NEW DIAGRAMS


let diagramNum = 0;

const addDiagram = (x, y, diagramWidth, type) => {
  diagramNum++;
  const diagram = document.createElement("div");
  diagram.id = "diagram" + diagramNum;
  diagram.classList.add("diagram");

  diagram.style.cssText = `
    width: ${diagramWidth}px;
    display: flex;
    flex-direction: column;
    background-color: rgb(230, 230, 230);
    border: 2px solid black;
    position: absolute;
    left: ${x}px;
    top: ${y}px;

    scale: ${scale};
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
      font-size: ${defaultLineHeight}px;
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
      font-size: ${defaultLineHeight}px;`

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
      font-size: ${defaultLineHeight}px;
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
      font-size: ${defaultLineHeight}px;
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
      font-size: ${defaultLineHeight}px;
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
      font-size: ${defaultLineHeight}px;
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
      font-size: ${defaultLineHeight}px;`

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
      font-size: ${defaultLineHeight}px;
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
      font-size: ${defaultLineHeight}px;
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

  const diagramInfo = {
    external: {
      type: type,
      xPosition: x,
      yPosition: y,
      width: diagramWidth,
      height: 190 // change later
    },
    internal: {
      name: "",
      fields: "",
      methods: ""
    }
  }

  // diagramsData.data.push()
}

// ADDING DIAGRAMS TO THE GRID
// for (let diagram in diagramsData) {
//   // need to add diagram.ySize in relation to the lines
//   addDiagram(diagram.xPosition, diagram.yPosition, diagram.xSize, diagram.objecttype)
// }
document.querySelector("#addclass > img").addEventListener("click", (e) => {
  addDiagram(((grid.offsetWidth - (defaultDiagramWidth*scale))/2),
   ((grid.offsetHeight - (defaultLineHeight*11*scale))/2), defaultDiagramWidth,
   "CLASSDIAGRAM")
  
  diagrams.data.push({
    external: {
      type: "class",
      xPosition: (grid.offsetWidth - (defaultDiagramWidth*scale))/2,
      yPosition: (grid.offsetHeight - (defaultLineHeight*11*scale))/2,
      width: defaultDiagramWidth*scale,
      height: defaultLineHeight*11*scale,
    },
    internal: {

    }
  })

  snap();
})
document.querySelector("#addinterface > img").addEventListener("click", (e) => {
  addDiagram(((grid.offsetWidth - (defaultDiagramWidth*scale))/2),
  ((grid.offsetHeight -defaultLineHeight*6*scale)/2), defaultDiagramWidth,
  "INTERFACEDIAGRAM")

  diagrams.data.push({
    external: {
      type: "interface",
      xPosition: (grid.offsetWidth - (defaultDiagramWidth*scale))/2,
      yPosition: (grid.offsetHeight - (defaultLineHeight*11*scale))/2,
      width: defaultDiagramWidth*scale,
      height: defaultLineHeight*6*scale,
    },
    internal: {

    }
  })
  snap();
})
document.querySelector("#addabstractclass > img").addEventListener("click", (e) => {
  addDiagram(((grid.offsetWidth - (defaultDiagramWidth*scale))/2),
  ((grid.offsetHeight - defaultLineHeight*11*scale)/2), defaultDiagramWidth,
  "ABSTRACTCLASSDIAGRAM")

  diagrams.data.push({
    external: {
      type: "abstractclass",
      xPosition: (grid.offsetWidth - (defaultDiagramWidth*scale))/2,
      yPosition: (grid.offsetHeight - (defaultLineHeight*11*scale))/2,
      width: defaultDiagramWidth*scale,
      height: defaultLineHeight*11*scale,
    },
    internal: {

    }
  })
  snap();
})
document.querySelector("#addexception > img").addEventListener("click", (e) => {
  addDiagram(((grid.offsetWidth - (defaultDiagramWidth*scale))/2),
  ((grid.offsetHeight - defaultLineHeight*1*scale)/2), defaultDiagramWidth,
  "EXCEPTIONDIAGRAM")

  diagrams.data.push({
    external: {
      type: "exception",
      xPosition: (grid.offsetWidth - (defaultDiagramWidth*scale))/2,
      yPosition: (grid.offsetHeight - (defaultLineHeight*11*scale))/2,
      width: defaultDiagramWidth*scale,
      height: defaultLineHeight*1*scale,
    },
    internal: {

    }
  })
  snap();
})

snap();

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

  if (type === "COMPOSITION" || type === "INHERITANCE") {
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
  } else if (type === "IMPLEMENTATION") {
    line.style.cssText = `
    position: absolute;
    height: 2px;
    transform-origin: top left;
    width: ${distance}px;
    top: ${ay}px;
    left: ${ax}px;
    transform: rotate(${degree}deg);
    border-top: 1px dotted black;
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

const headDraw = (ax, ay, bx, by, type) => {

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

  triangle.classList.add("arrowhead");

  document.querySelector("#arrows").appendChild(triangle);

  if (type === "INHERITANCE" || type === "IMPLEMENTATION") {
    const innerTriangle = document.createElement("div")

    let degree = 0;
  
    if (ax <= bx) {
      const calc = Math.atan((by - ay) / (bx - ax));
      degree = calc * 180 / Math.PI;
    }
    else {
      const calc = Math.atan((by - ay) / (bx - ax));
      degree = -(180 - (90 + (calc * 180 / Math.PI)));
    }

    length = Math.sqrt((by - ay)**2 + (bx - ax)**2)
  
    innerTriangle.style.cssText = `
      width: 0;
      height: 0;
      position: absolute;
      top: ${-4}px;
      left: ${-6}px;
      border: solid 6px;
      border-color: transparent transparent white transparent;
      
    }`

    triangle.appendChild(innerTriangle);
  } else if (type === "COMPOSITION") {

    const triangle2 = document.createElement("div")

    let degree = 0;

  
    triangle2.style.cssText = `
      width: 0;
      height: 0;
      position: absolute;
      top: ${9}px;
      left: ${-10}px;
      border: solid 10px;
      border-color: black transparent transparent transparent;
      transform: rotate(${degree}deg);
    }`

    triangle.appendChild(triangle2);
  }

};


const arrowData = [
  {
    xPoints: [],
    yPoints: [],
    origin: null,
    destination: null,
    type: null
  }
]

// const arrows = axios.get("URL");
// const arrowData = arrows.data;

const drawArrows = (arrowData) => {
  for (let arrow in arrowData) {
    for (let i = 0; i < arrowData[arrow].xPoints.length - 1; i++) {
      let x1 = arrowData[arrow].xPoints[i];
      let y1 = arrowData[arrow].yPoints[i];
  
      let x2 = arrowData[arrow].xPoints[i + 1];
      let y2 = arrowData[arrow].yPoints[i + 1];
  
      lineDraw(x1, y1, x2, y2, arrowData[arrow].type)
      pointDraw(x1, y1)
  
      if (i === arrowData[arrow].xPoints.length - 2) {
        headDraw(x1, y1, x2, y2, arrowData[arrow].type)
      }
    }
  
  }
}

// ADDING ARROWS
const addArrow = (arrowData, vertices, type) => {
  const originX = grid.offsetWidth/2
  const originY = grid.offsetHeight/2;

  if (vertices === "0") {
    arrowData.push({
      xPoints: [originX - 100, originX + 100],
      yPoint: [originY, originY],
      origin: null,
      destination: null,
      type: type
    })
  }
  else if (vertices === "1") {
    arrowData.push({
      xPoints: [originX - 100, originX, originX + 100],
      yPoints: [originY, originY, originY],
      origin: null,
      destination: null,
      type: type
    })

  }
  else if (vertices === "2") {
    arrowData.push({
      xPoints: [originX - 100, originX - 33, originX + 33, originX + 100],
      yPoints: [originY, originY, originY, originY],
      origin: null,
      destination: null,
      type: type
    })
  } else if (vertices === "3") {
    arrowData.push({
      xPoints: [originX - 100, originX - 50, originX, originX + 50, originX + 100],
      yPoints: [originY, originY, originY, originY, originY],
      origin: null,
      destination: null,
      type: type
    })
  }
  else if (vertices === "4") {
    arrowData.push({
    xPoints: [originX - 100, originX - 60, originX - 20, originX + 20, originX + 60, originX + 100],
    yPoints: [originY, originY, originY, originY, originY, originY],
    origin: null,
    destination: null,
    type: type
  })
}

  drawArrows(arrowData);

}

document.querySelector("#inheritancearrow").addEventListener("click", (e) => {
  addArrow(arrowData, document.querySelector("form > select").value, "INHERITANCE")
  drawArrows();

})
document.querySelector("#compositionarrow").addEventListener("click", (e) => {
  addArrow(arrowData, document.querySelector("form > select").value, "COMPOSITION")
  drawArrows();
})
document.querySelector("#implementationarrow").addEventListener("click", (e) => {
  addArrow(arrowData, document.querySelector("form > select").value, "IMPLEMENTATION")
  drawArrows();
})

drawArrows();


// ARROW DRAGGING
let pointIndex = null;
let selectedArrow = null;
let end = null;
let move = null;
let dragging = false;


grid.addEventListener('mousedown', (e) => {
let x = e.x - grid.offsetLeft;
let y = e.y - grid.offsetTop;

  for (let arrow of arrowData) {
    for (let i = 0; i < arrow.xPoints.length ; i++) {
      let xPoint = arrow.xPoints[i];
      let yPoint = arrow.yPoints[i];

      if (Math.sqrt((xPoint - x)**2 + (yPoint - y)**2) <= 20) {
        pointIndex = i;
        dragging = true;
        selectedArrow = arrow;
      }
    }
  }
})

grid.addEventListener("mousemove", (e) => {
  if (dragging) {

    selectedArrow.xPoints[pointIndex] = e.x - grid.offsetLeft;
    selectedArrow.yPoints[pointIndex] = e.y - grid.offsetTop;

    document.querySelector("#arrows").innerHTML = "";

    for (let selectedArrow of arrowData) {
      for (let i = 0; i < selectedArrow.xPoints.length - 1; i++) {
        let x1 = selectedArrow.xPoints[i];
        let y1 = selectedArrow.yPoints[i];
    
        let x2 = selectedArrow.xPoints[i + 1];
        let y2 = selectedArrow.yPoints[i + 1];
    
        lineDraw(x1, y1, x2, y2, selectedArrow.type);
        pointDraw(x1, y1);

        if (i === selectedArrow.xPoints.length - 2) {
          headDraw(x1, y1, x2, y2, selectedArrow.type)
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


//   let diagrams = axios.get("SERVERURL")
// const diagramsData = diagrams.data;

// let diagram = {
//   external: {
//     type: "class",
//     xPosition: 750,
//     yPosition: 250,
//     width: 164,
//     height: 190,
//   }
// }

// diagrams = {
//   data: [
//     diagram = {
//       external: {
//         type: "class",
//         xPosition: 750,
//         yPosition: 250,
//         width: 164,
//         height: 190,
//       },
//       internal: {

//       }
//     }
//   ]
// }

  const finalX = selectedArrow.xPoints[pointIndex];
  const finalY = selectedArrow.yPoints[pointIndex];

  const previousX = selectedArrow.xPoints[pointIndex - 1];
  const previousY = selectedArrow.yPoints[pointIndex - 1];

  for (let diagram of diagrams.data) {
    console.dir(diagram)
    const externalInfo = diagram.external;

    console.log(previousX <= externalInfo.xPosition, previousY >= externalInfo.yPosition + externalInfo.height)
    if ((pointIndex === selectedArrow.xPoints.length - 1) && (selectedArrow.destination === null)) {
      // console.log(externalInfo.xPosition, externalInfo.xPosition + externalInfo.width, externalInfo.yPosition, externalInfo.yPosition + externalInfo.height)
      // console.log(finalX, finalY)
      // console.log(finalX >= externalInfo.xPosition, finalX <= externalInfo.xPosition + externalInfo.width, finalY >= externalInfo.yPosition, finalY <= externalInfo.yPosition + externalInfo.height)
      console.log(finalX, externalInfo.xPosition)
      if (((finalX >= externalInfo.xPosition) && (finalX <= externalInfo.xPosition + externalInfo.width)) && 
      ((finalY >= externalInfo.yPosition) && (finalY <= externalInfo.yPosition + externalInfo.height))) {

        const m = -(finalY - previousY)/(finalX - previousX);

        console.log("slope", m)

        // top left
        if ((previousX <= externalInfo.xPosition) && (previousY <= externalInfo.yPosition)) {
          const yIntersect = m*externalInfo.xPosition + finalY - m*finalX
          const xIntersect = (externalInfo.yPosition - finalY + m*finalX)/m
          if ((yIntersect >= externalInfo.yPosition) && (yIntersect <= externalInfo.yPosition + externalInfo.height)) {
            selectedArrow.xPoints[pointIndex] = externalInfo.xPosition;
            selectedArrow.yPoints[pointIndex] = yIntersect;
          } else {
            selectedArrow.xPoints[pointIndex] = xIntersect;
            selectedArrow.yPoints[pointIndex] = externalInfo.yPosition;
          }

          drawArrows(arrowData);
          console.log(1)

        // top
        } else if (((previousX > externalInfo.xPosition) && (previousX < externalInfo.xPosition + externalInfo.width)) &&
        (previousY <= externalInfo.yPosition)) {
          const xIntersect = (externalInfo.yPosition - finalY + m*finalX)/m
          selectedArrow.xPoints[pointIndex] = xIntersect;
          selectedArrow.yPoints[pointIndex] = externalInfo.yPosition;

          drawArrows(arrowData);
          console.log(2)

        // top right
        } else if ((previousX >= externalInfo.xPosition) && (previousY <= externalInfo.yPosition)) {
          const yIntersect = m*(externalInfo.xPosition + externalInfo.width) + finalY - m*finalX
          const xIntersect = (externalInfo.yPosition - finalY + m*finalX)/m
          if ((yIntersect >= externalInfo.yPosition) && (yIntersect <= externalInfo.yPosition + externalInfo.height)) {
            selectedArrow.xPoints[pointIndex] = externalInfo.xPosition + externalInfo.width;
            selectedArrow.yPoints[pointIndex] = yIntersect;
          } else {
            selectedArrow.xPoints[pointIndex] = xIntersect;
            selectedArrow.yPoints[pointIndex] = externalInfo.yPosition;
          }

          drawArrows(arrowData);
          console.log(3)
        // left
       } else if ((previousX <= externalInfo.xPosition) && ((previousY > externalInfo.yPosition)
         && (previousY < externalInfo.yPosition + externalInfo.height))) {
          const yIntersect = m*externalInfo.xPosition + finalY - m*finalX
          selectedArrow.xPoints[pointIndex] = externalInfo.xPosition;
          selectedArrow.yPoints[pointIndex] = yIntersect;

          drawArrows(arrowData);
          console.log(4)

        // right
        } else if ((previousX >= externalInfo.xPosition) && ((previousY > externalInfo.yPosition)
        && (previousY < externalInfo.xPosition + externalInfo.width))) {
          const yIntersect = m*(externalInfo.xPosition + externalInfo.width) + finalY - m*finalX
          selectedArrow.xPoints[pointIndex] = externalInfo.xPosition + externalInfo.width;
          selectedArrow.yPoints[pointIndex] = yIntersect;

          drawArrows(arrowData);
          console.log(5)

        // bottom left
        } else if ((previousX <= externalInfo.xPosition) && (previousY >= externalInfo.yPosition + externalInfo.height)) {
          console.log(finalY + m*finalX)
          const yIntersect = m*externalInfo.xPosition + finalY + m*finalX
          const xIntersect = ((externalInfo.yPosition + externalInfo.height) - finalY + m*finalX)/m
          if ((yIntersect >= externalInfo.yPosition) && (yIntersect <= externalInfo.yPosition + externalInfo.height)) {
            selectedArrow.xPoints[pointIndex] = externalInfo.xPosition;
            selectedArrow.yPoints[pointIndex] = yIntersect;
          } else {
            selectedArrow.xPoints[pointIndex] = xIntersect;
            selectedArrow.yPoints[pointIndex] = externalInfo.yPosition;
          }

          drawArrows(arrowData);
          console.log(6, externalInfo.yPosition, yIntersect, externalInfo.yPosition + externalInfo.height)
        // bottom
        } else if (((previousX > externalInfo.xPosition) && (previousX < externalInfo.xPosition + externalInfo.width)) && (previousY >= externalInfo.yPosition + externalInfo.height)) {
          const xIntersect = ((externalInfo.yPosition + externalInfo.height) - finalY + m*finalX)/m
          selectedArrow.xPoints[pointIndex] = xIntersect;
          selectedArrow.yPoints[pointIndex] = externalInfo.yPosition;

          drawArrows(arrowData);
        // bottom right
        } else if ((previousX >= externalInfo.xPosition + externalInfo.width) && (previousY >= externalInfo.yPosition + externalInfo.height)) {
          const yIntersect = m*(externalInfo.xPosition + externalInfo.width) + finalY - m*finalX
          const xIntersect = ((externalInfo.yPosition + externalInfo.height) - finalY + m*finalX)/m
          if ((yIntersect >= externalInfo.yPosition) && (yIntersect <= externalInfo.yPosition + externalInfo.height)) {
            selectedArrow.xPoints[pointIndex] = externalInfo.xPosition + externalInfo.width;
            selectedArrow.yPoints[pointIndex] = yIntersect;
          } else {
            selectedArrow.xPoints[pointIndex] = xIntersect;
            selectedArrow.yPoints[pointIndex] = externalInfo.yPosition + externalInfo.height;
          }

          drawArrows(arrowData);
          console.log(7)
        }
      }
    }
  }

  dragging = false;
  pointIndex = null;
  selectedArrow = null;
})

// ARROW DIAGRAM INTERACTION


