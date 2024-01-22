// DOUBLE ARROW WHEN MULTIPLE ARROWS ARE CONNECTED
// ZOOM NO WORK
// ADD DELETE
// ARROWS DRAG ALONG WITH THE DIAGRAM

let arrowID = 0;
let diagramID = 0;
const grid = document.getElementById('grid');
const gridBounds = grid.getBoundingClientRect();
let scale = 1;
let defaultDiagramWidth = 164;
let defaultLineHeight = 14;

let diagrams = axios.get("SERVERURL")
const diagramsData = diagrams.data;

const arrowData = []

// const arrows = axios.get("URL");
// const arrowData = arrows.data;

diagrams = {
  data: []
}

// ARROWS
// FUNCTIONS FOR ARROWS
const lineDraw = (ax, ay, bx, by, type, id) => {
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
  line.classList.add(id)

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

const pointDraw = (x, y, id) => {
  const dot = document.createElement("div")
  const radius = 4

  dot.classList.add(id);

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

const headDraw = (ax, ay, bx, by, type, id) => {

  const triangle = document.createElement("div")

  triangle.classList.add(id)

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

const drawArrows = (arrowData) => {
  for (let arrow in arrowData) {
    for (let i = 0; i < arrowData[arrow].xPoints.length - 1; i++) {
      let x1 = arrowData[arrow].xPoints[i];
      let y1 = arrowData[arrow].yPoints[i];
  
      let x2 = arrowData[arrow].xPoints[i + 1];
      let y2 = arrowData[arrow].yPoints[i + 1];
  
      lineDraw(x1, y1, x2, y2, arrowData[arrow].type, arrow)
      pointDraw(x1, y1, arrow)
  
      if (i === arrowData[arrow].xPoints.length - 2) {
        headDraw(x1, y1, x2, y2, arrowData[arrow].type, arrow)
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
      type: type,
      id: arrowID
    })
  }
  else if (vertices === "1") {
    arrowData.push({
      xPoints: [originX - 100, originX, originX + 100],
      yPoints: [originY, originY, originY],
      origin: null,
      destination: null,
      type: type,
      id: arrowID
    })

  }
  else if (vertices === "2") {
    arrowData.push({
      xPoints: [originX - 100, originX - 33, originX + 33, originX + 100],
      yPoints: [originY, originY, originY, originY],
      origin: null,
      destination: null,
      type: type,
      id: arrowID
    })
  } else if (vertices === "3") {
    arrowData.push({
      xPoints: [originX - 100, originX - 50, originX, originX + 50, originX + 100],
      yPoints: [originY, originY, originY, originY, originY],
      origin: null,
      destination: null,
      type: type,
      id: arrowID
    })
  }
  else if (vertices === "4") {
    arrowData.push({
    xPoints: [originX - 100, originX - 60, originX - 20, originX + 20, originX + 60, originX + 100],
    yPoints: [originY, originY, originY, originY, originY, originY],
    origin: null,
    destination: null,
    type: type,
    id: arrowID
  })
}

  arrowID++;

  drawArrows(arrowData);
}

const loadArrow = (xPoints, yPoints, origin, destination, type) => {
  arrowData.push({
    xPoints: [...xPoints],
    yPoints: [...yPoints],
    origin: origin,
    destination: destination,
    type: type,
    id: arrowID
  })

  arrowID++;

  drawArrows(arrowData);
}

document.querySelector("#inheritancearrow").addEventListener("click", (e) => {
  addArrow(arrowData, document.querySelector("form > select").value, "INHERITANCE", arrowID)
  drawArrows();

})
document.querySelector("#compositionarrow").addEventListener("click", (e) => {
  addArrow(arrowData, document.querySelector("form > select").value, "COMPOSITION", arrowID)
  drawArrows();
})
document.querySelector("#implementationarrow").addEventListener("click", (e) => {
  addArrow(arrowData, document.querySelector("form > select").value, "IMPLEMENTATION", arrowID)
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

      if (i === arrow.xPoints.length - 1) {
        arrow.destination = null;
      } else if (i === 0) {
        arrow.origin = null;
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

// ARROW DIAGRAM INTERACTION

  const finalX = selectedArrow.xPoints[pointIndex];
  const finalY = selectedArrow.yPoints[pointIndex];

  const previousX = selectedArrow.xPoints[pointIndex - 1];
  const previousY = selectedArrow.yPoints[pointIndex - 1];

  for (let i = 0; i < selectedArrow.xPoints.length; i++) {
    console.log(`(${selectedArrow.xPoints[i]},${selectedArrow.yPoints[i]})`)
  }
  
  for (let diagram of diagrams.data) {
    console.dir(diagram)
    const externalInfo = diagram.external;
    console.log(document.querySelector(".diagram").offsetHeight)
    console.log(externalInfo.height)

    if ((pointIndex === selectedArrow.xPoints.length - 1) && (selectedArrow.destination === null)) {
      // console.log(externalInfo.xPosition, externalInfo.xPosition + externalInfo.width, externalInfo.yPosition, externalInfo.yPosition + externalInfo.height)
      // console.log(finalX, finalY)
      // console.log(finalX >= externalInfo.xPosition, finalX <= externalInfo.xPosition + externalInfo.width, finalY >= externalInfo.yPosition, finalY <= externalInfo.yPosition + externalInfo.height)
      if (((finalX >= externalInfo.xPosition) && (finalX <= externalInfo.xPosition + externalInfo.width)) && 
      ((finalY >= externalInfo.yPosition) && (finalY <= externalInfo.yPosition + externalInfo.height))) {
        
        selectedArrow.destination = diagram.external.id;

        const left = finalX - externalInfo.xPosition;
        const right = externalInfo.xPosition + externalInfo.width - finalX;
        const top = finalY - externalInfo.yPosition;
        const bottom = externalInfo.yPosition + externalInfo.height - finalY;

        console.log(externalInfo.xPosition, externalInfo.yPosition)
        console.log("he", left, right, top, bottom)

        const min = Math.min(left, right, top, bottom);

        if (min === left) {
          selectedArrow.xPoints[pointIndex] = externalInfo.xPosition;
          selectedArrow.yPoints[pointIndex] = finalY;
          
        } else if (min === top) {
          selectedArrow.xPoints[pointIndex] = finalX;
          selectedArrow.yPoints[pointIndex] = externalInfo.yPosition;
        } else if (min === right) {
          selectedArrow.xPoints[pointIndex] = externalInfo.xPosition + externalInfo.width;
          selectedArrow.yPoints[pointIndex] = finalY;
        } else if (min === bottom) {
          selectedArrow.xPoints[pointIndex] = finalX;
          selectedArrow.yPoints[pointIndex] = externalInfo.yPosition + externalInfo.height;
        }

        const childArrows = document.querySelector("#arrows").children;

        console.log(arrowData)
        console.log(childArrows)

        for (let i = 0; i < arrowData.length; i++) {
          const currentArrowData = arrowData[i];
          console.log(currentArrowData)
          
          console.log("cheese", currentArrowData.destination, externalInfo.id)

          if (currentArrowData.destination === externalInfo.id) {
            for (let j = 0; j < (2 * currentArrowData.xPoints.length) - 1; j++) {
              const childArrow = childArrows[0];
              console.log(childArrow)
              childArrow.remove()
            }
          }
        }
        drawArrows(arrowData)
      } else {
        console.log(selectedArrow.destination)
        selectedArrow.destination = null;
      }
    } else if ((pointIndex === 0) && (selectedArrow.origin === null)) {
      // console.log(externalInfo.xPosition, externalInfo.xPosition + externalInfo.width, externalInfo.yPosition, externalInfo.yPosition + externalInfo.height)
      // console.log(finalX, finalY)
      // console.log(finalX >= externalInfo.xPosition, finalX <= externalInfo.xPosition + externalInfo.width, finalY >= externalInfo.yPosition, finalY <= externalInfo.yPosition + externalInfo.height)
      if (((finalX >= externalInfo.xPosition) && (finalX <= externalInfo.xPosition + externalInfo.width)) && 
      ((finalY >= externalInfo.yPosition) && (finalY <= externalInfo.yPosition + externalInfo.height))) {
        
        selectedArrow.origin = diagram.external.id;

        const left = finalX - externalInfo.xPosition;
        const right = externalInfo.xPosition + externalInfo.width - finalX;
        const top = finalY - externalInfo.yPosition;
        const bottom = externalInfo.yPosition + externalInfo.height - finalY;

        const min = Math.min(left, right, top, bottom);

        if (min === left) {
          selectedArrow.xPoints[pointIndex] = externalInfo.xPosition;
          selectedArrow.yPoints[pointIndex] = finalY;
          
        } else if (min === top) {
          selectedArrow.xPoints[pointIndex] = finalX;
          selectedArrow.yPoints[pointIndex] = externalInfo.yPosition;
        } else if (min === right) {
          selectedArrow.xPoints[pointIndex] = externalInfo.xPosition + externalInfo.width;
          selectedArrow.yPoints[pointIndex] = finalY;
        } else if (min === bottom) {
          selectedArrow.xPoints[pointIndex] = finalX;
          selectedArrow.yPoints[pointIndex] = externalInfo.yPosition + externalInfo.height;
        }

        const childArrows = document.querySelector("#arrows").children;

        for (let i = 0; i < arrowData.length; i++) {
          const currentArrowData = arrowData[i];

          if (currentArrowData.origin === externalInfo.id) {
            for (let j = 0; j < (2 * currentArrowData.xPoints.length) - 1; j++) {
              const childArrow = childArrows[0];
              console.log(childArrow)
              childArrow.remove()
            }
          }
        }
        drawArrows(arrowData)
      } else {
        selectedArrow.origin = null;
      }
    }
  }

  dragging = false;
  pointIndex = null;
  selectedArrow = null;
})

// LOAD IN DIAGRAMS

// ADDING NEW DIAGRAMS


let diagramNum = 0;

const addDiagram = (diagrams, x, y, diagramWidth, type, nameText="", methodsText="", fieldsText="") => {
  const diagram = document.createElement("div");
  diagram.id = "diagram" + diagramID;
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
    name.value = nameText;
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
    fields.value = fieldsText
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
    methods.value = methodsText
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
    name.value = nameText
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
    methods.value = methodsText
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
    name.value = nameText
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
    fields.value = fieldsText
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
    methods.value = methodsText;
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
    name.value = nameText;
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
      height: 223, // change later
      id: diagramID
    }
  }

  diagramID++;

  console.log(diagrams.data)
  diagrams.data.push(diagramInfo)
}

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
    
      // for (let arrow of arrowData) {
      //   console.log(arrow.destination)
      //   console.log(diagrams.data[Number(element.id.slice(7))].external.id)
      //   if (arrow.destination === diagrams.data[Number(element.id.slice(7))].external.id) {
      //     arrow.xPoints[-1] += event.dx / scale;
      //     arrow.yPoints[-1] += event.dy / scale;
      //   }
      // }

      console.log(arrowData.length)
      for (let i = 0; i < arrowData.length; i++) {
        // console.log(Number(arrowData[i].destination))
        // console.log(diagrams.data[Number(element.id.slice(7))].external.id)
        if ((arrowData[i].destination !== null) && (Number(arrowData[i].destination) === diagrams.data[Number(element.id.slice(7))].external.id)) {
          // console.log(arrowData[i])
          // console.log(arrowData[i].xPoints[-1], arrowData[i].yPoints[-1])
          arrowData[i].xPoints[arrowData[i].xPoints.length - 1] += event.dx / scale;
          arrowData[i].yPoints[arrowData[i].xPoints.length - 1] += event.dy / scale;
          arrowData[i].destination = diagrams.data[Number(element.id.slice(7))].external.id
          drawArrows(arrowData)
        }
      }

      const childArrows = document.querySelector("#arrows").children;

      // console.log(arrowData)
      // console.log(childArrows)

      for (let i = 0; i < arrowData.length; i++) {
        const currentArrowData = arrowData[i];
        // console.log(currentArrowData)
        
        // console.log("cheese", currentArrowData.destination, diagrams.data[Number(element.id.slice(7))].external.id)

        if (currentArrowData.destination === diagrams.data[Number(element.id.slice(7))].external.id) {
          for (let j = 0; j < (2 * currentArrowData.xPoints.length) - 1; j++) {
            const childArrow = childArrows[0];
            childArrow.remove()
          }
        }
      }
    })
  })
}



// ADDING DIAGRAMS TO THE GRID
// for (let diagram in diagramsData) {
//   // need to add diagram.ySize in relation to the lines
//   addDiagram(diagram.xPosition, diagram.yPosition, diagram.xSize, diagram.objecttype)
// }
document.querySelector("#addclass > img").addEventListener("click", (e) => {
  addDiagram(diagrams, ((grid.offsetWidth - (defaultDiagramWidth*scale))/2),
   ((grid.offsetHeight - (defaultLineHeight*16*scale))/2), defaultDiagramWidth,
   "CLASSDIAGRAM")
  
  
  // diagrams.data.push({
  //   external: {
  //     type: "class",
  //     xPosition: (grid.offsetWidth - (defaultDiagramWidth*scale))/2,
  //     yPosition: (grid.offsetHeight - (defaultLineHeight*11*scale))/2,
  //     width: defaultDiagramWidth*scale,
  //     height: defaultLineHeight*21*scale,
  //     id: id
  //   },
  //   internal: {

  //   }
  // })

  snap();
})
document.querySelector("#addinterface > img").addEventListener("click", (e) => {
  addDiagram(diagrams, ((grid.offsetWidth - (defaultDiagramWidth*scale))/2),
  ((grid.offsetHeight -defaultLineHeight*6*scale)/2), defaultDiagramWidth,
  "INTERFACEDIAGRAM")

  // diagrams.data.push({
  //   external: {
  //     type: "interface",
  //     xPosition: (grid.offsetWidth - (defaultDiagramWidth*scale))/2,
  //     yPosition: (grid.offsetHeight - (defaultLineHeight*11*scale))/2,
  //     width: defaultDiagramWidth*scale,
  //     height: defaultLineHeight*6*scale,
  //   },
  //   internal: {

  //   }
  // })
  snap();
})
document.querySelector("#addabstractclass > img").addEventListener("click", (e) => {
  addDiagram(diagrams, ((grid.offsetWidth - (defaultDiagramWidth*scale))/2),
  ((grid.offsetHeight - defaultLineHeight*11*scale)/2), defaultDiagramWidth,
  "ABSTRACTCLASSDIAGRAM")

  // diagrams.data.push({
  //   external: {
  //     type: "abstractclass",
  //     xPosition: (grid.offsetWidth - (defaultDiagramWidth*scale))/2,
  //     yPosition: (grid.offsetHeight - (defaultLineHeight*11*scale))/2,
  //     width: defaultDiagramWidth*scale,
  //     height: defaultLineHeight*11*scale,
  //   },
  //   internal: {

  //   }
  // })
  snap();
})
document.querySelector("#addexception > img").addEventListener("click", (e) => {
  addDiagram(diagrams, ((grid.offsetWidth - (defaultDiagramWidth*scale))/2),
  ((grid.offsetHeight - defaultLineHeight*1*scale)/2), defaultDiagramWidth,
  "EXCEPTIONDIAGRAM")

  // diagrams.data.push({
  //   external: {
  //     type: "exception",
  //     xPosition: (grid.offsetWidth - (defaultDiagramWidth*scale))/2,
  //     yPosition: (grid.offsetHeight - (defaultLineHeight*11*scale))/2,
  //     width: defaultDiagramWidth*scale,
  //     height: defaultLineHeight*1*scale,
  //   },
  //   internal: {

  //   }
  // })
  snap();
})

snap();

// DIAGRAM SNAPPING

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

const loadUML = async (username, projectName) => {
  await fetch("/frontend/loadUML", {
      method: "POST",
      headers: {
          "Accept": "application/json",
          "Content-Type": "application/json"
      },
      body: JSON.stringify({
          username: username,
          projectName: projectName
      })
  })
      .then(res => {
          return res.json();
      })
      .then(res => {
        // return "{\n\"ProjectName\":" + "\"" + project.getProjectName() +  "\"" + ",\n" +
        // "\"diagramIdCount\": 0,\n" +
        // "\"arrowIdCount\": 0,\n" +
        // "\"classDiagrams\": [],\n" +
        // "\"interfaceDiagrams\": [],\n" +
        // "\"exceptionDiagrams\": [],\n" +
        // "\"arrows\": [],\n}";

        for (let classDiagram in res.classDiagram) {

          let fieldsText = "";
          for (let field of classDiagram.fields) {
            fieldsText += field + "\n";
          }

          let methodsText = "";
          for (let method of classDiagram.methods) {
            methodsText += method + "\n";
          }

          if (!classDiagram.isAbstract) {
            addDiagram(classDiagram.xPosition, classDiagram.yPosition, classDiagram.width, "CLASS", classDiagram.name,
             methodsText, fieldsText)
          } else {
            addDiagram(classDiagram.xPosition, classDiagram.yPosition, classDiagram.width, "ABSTRACTCLASS", classDiagram.name,
            methodsText, fieldsText)
          }
        }

        for (let interfaceDiagram in res.interfaceDiagrams) {

          let methodsText = "";
          for (let method of interfaceDiagram.methods) {
            methodsText += method + "\n";
          }

          addDiagram(interfaceDiagram.xPosition, interfaceDiagram.yPosition, interfaceDiagram.width, "INTERFACE", interfaceDiagram.name,
             fieldsText)
        }
        
        for (let exceptionDiagram in res.exceptionDiagrams) {

          addDiagram(exceptionDiagram.xPosition, exceptionDiagram.yPosition, exceptionDiagram.width, "EXCEPTION", exceptionDiagram.name)
        }

        for (let arrow of res.arrows) {
          loadArrow(arrow.xPoints, arrow.yPoints, arrow.origin, arrow.destination, arrow.type);
        }

      })
      .catch(err => {
        console.log(err)
          console.log("ERROR RETRIEVING USER DATA")
          // window.location.href = "http://localhost:5069/frontend/homepage/homepage.html"
      })
}

window.addEventListener("DOMContentLoaded", () => {
  loadUML(window.localStorage.getItem("username"), window.localStorage.getItem("projectName"))
})

const UMLFormat = () => {

}

// SENDING BACK UML INFO TO THE SERVER

const saveUML = async () => {
  const classes = [];
  const interfaces = [];
  const exceptions = [];


  for (let diagram of diagrams.data) {
    if (diagram.external.type === "CLASSDIAGRAM") {
      classes.push({
        classId: diagram.external.id,
        className: document.querySelector(`#diagram${diagram.external.id} > .name`).value,
        xPosition: diagram.external.xPosition,
        yPosition: diagram.external.yPosition,
        xSize: diagram.external.xSize,
        ySize: diagram.external.ySize,
        isAbstract: false,
        fields: [...document.querySelector(`#diagram${diagram.external.id} > .fields`).value.split("\n")],
        methods: [...document.querySelector(`#diagram${diagram.external.id} > .methods`).value.split("\n")]
      })
    } else if (diagram.external.type === "ABSTRACTCLASSDIAGRAM") {
      
      classes.push({
        classId: diagram.external.id,
        className: document.querySelector(`#diagram${diagram.external.id} > .name`).value,
        xPosition: diagram.external.xPosition,
        yPosition: diagram.external.yPosition,
        xSize: diagram.external.xSize,
        ySize: diagram.external.ySize,
        isAbstract: true,
        fields: [...document.querySelector(`#diagram${diagram.external.id} > .fields`).textContent.split("\n")],
        methods: [...document.querySelector(`#diagram${diagram.external.id} > .methods`).value.split("\n")]
      })
    } else if (diagram.external.type === "INTERFACEDIAGRAM") {
      
      interfaces.push({
        classId: diagram.external.id,
        className: document.querySelector(`#diagram${diagram.external.id} > .name`).value,
        xPosition: diagram.external.xPosition,
        yPosition: diagram.external.yPosition,
        xSize: diagram.external.xSize,
        ySize: diagram.external.ySize,
        methods: [...document.querySelector(`#diagram${diagram.external.id} > .methods`).value.split("\n")]
      })
    } else if (diagram.external.type === "EXCEPTIONDIAGRAM") {
      
      exceptions.push({
        classId: diagram.external.id,
        className: document.querySelector(`#diagram${diagram.external.id} > .name`).value,
        xPosition: diagram.external.xPosition,
        yPosition: diagram.external.yPosition,
        xSize: diagram.external.xSize,
        ySize: diagram.external.ySize,
      })
    } 
  }

  const arrows = [];

  for (let arrow of arrowData) {
    arrows.push({
      origin: arrow.origin,
      destination: arrow.destination,
      arrowType: arrow.arrowType,
      arrowId: arrow.id,
      xPoints: arrow.xPoints,
      yPoints: arrow.yPoints
    })
  }

  await fetch("/frontend/saveUML", {
    method: "POST",
    headers: {
        "Accept": "application/json",
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        projectName: window.localStorage.getItem("projectName") + "##",
        diagramCount: diagramID + "##",
        arrowCount: arrowID + "##",
        classDiagrams: JSON.stringify(classes) + "##",
        interfaceDiagrams: JSON.stringify(interfaces) + "##",
        exceptionDiagrams: JSON.stringify(exceptions) + "##",
        arrows: JSON.stringify(arrows) + "##"
    })
})
    .then(res => {
        return res.json();
    })
    .then(res => {
      console.log(res)
    })
    .catch(err => {
      console.log(err)
    })
}

const share = async (projectName, sharee) => {
  await fetch("/frontend/shareProject", {
    method: "POST",
    headers: {
        "Accept": "application/json",
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        projectName: projectName,
        sharee: sharee,
    })
})
    .then(res => {
        return res.json();
    })
    .then(res => {
       console.log(res)
       if (!res.valid) {
         document.querySelector("#incorrect").style.display = "flex";
         document.querySelector("#warning").style.display = "flex";
      } else {
        document.querySelector("#incorrect").style.display = "none";
        document.querySelector("#warning").style.display = "none";
        shareModal.close();
      }
    })
    .catch(err => {
        console.log("ERROR RETRIEVING USER DATA")
        window.location.href = "http://localhost:5069/frontend/homepage/homepage.html"
    })
}

document.querySelector("#menu > form:nth-of-type(1)").addEventListener("submit", (e) => {
  e.preventDefault();
  console.log(diagrams.data);
  console.log(e)
  saveUML();
})

document.querySelector("#homeForm").addEventListener("submit", (e) => {
  e.preventDefault();
  window.location.href = "http://localhost:5069/frontend/homepage/homepage.html"
})

const shareModal = document.querySelector("dialog")

document.querySelector("#share").addEventListener("click", (e) => {
  e.preventDefault();
  shareModal.showModal();
})

const shareForm = document.querySelector("#shareForm");

shareForm.addEventListener("submit", (e) => {
    e.preventDefault();
    document.querySelector("#incorrect").style.display = "none";
    document.querySelector("#warning").style.display = "none";
    share(window.localStorage.getItem("projectName"), document.querySelector("#shareForm input").value);
    shareForm.reset();
})

const closeShare = document.querySelector("#shareModal .close")

closeShare.addEventListener("click", (e) => {
    document.querySelector("#incorrect").style.display = "flex";
    document.querySelector("#warning").style.display = "flex";
    console.log(e);
    shareModal.close();
})

grid.addEventListener("contextmenu", (e) => {
  e.preventDefault();
  if (MouseEvent.shiftKey) {

  }
})

grid.addEventListener("s")