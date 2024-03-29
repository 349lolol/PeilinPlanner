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

diagrams = {
  data: []
}

const lineDraw = (ax, ay, bx, by, type, id, arrow) => {
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
  line.classList.add(`--${id}`)

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

  line.addEventListener("contextmenu", (e) => {
    e.preventDefault()
    if (!e.shiftKey) {
      return;
    }

    const arrowID = e.target.classList[0];

    const arrowSegments = document.querySelectorAll(`#arrows > .${arrowID}`);
    
    for (let i = 0; i < arrowSegments.length; i++) {
      arrowSegments[i].remove();
    }


    const index = arrowData.indexOf(arrow);
  
    if (index !== -1) {
      arrowData.splice(index, 1);
    }


    arrowData.splice(arrowID.slice(2), 1)
    
    drawArrows(arrowData)
  })
}

const pointDraw = (x, y, id, arrow) => {
  const dot = document.createElement("div")
  const radius = 4

  dot.classList.add(`--${id}`)

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

  dot.addEventListener("contextmenu", (e) => {
    e.preventDefault();
    if (!e.shiftKey) {
      return;
    }

    const arrowID = e.target.classList[0];

    const arrowSegments = document.querySelectorAll(`#arrows > .${arrowID}`);
    
    for (let i = 0; i < arrowSegments.length; i++) {
      arrowSegments[i].remove();
    }

    const index = arrowData.indexOf(arrow);
  
    if (index !== -1) {
      arrowData.splice(index, 1);
    }

    arrowData.splice(arrowID.slice(2), 1)

    drawArrows(arrowData)
  })
};

const headDraw = (ax, ay, bx, by, type, id, arrow) => {

  const triangle = document.createElement("div")

  triangle.classList.add(`--${id}`)

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

  triangle.addEventListener("contextmenu", (e) => {
    e.preventDefault();
    if (!e.shiftKey) {
      return;
    }

    const arrowID = e.target.classList[0];

    const arrowSegments = document.querySelectorAll(`#arrows > .${arrowID}`);

    for (let i = 0; i < arrowSegments.length; i++) {
      arrowSegments[i].remove();
    }

    const index = arrowData.indexOf(arrow);
  
    if (index !== -1) {
      arrowData.splice(index, 1);
    }

    arrowData.splice(arrowID.slice(2), 1)

    drawArrows(arrowData)
  })

};

const drawArrows = (arrowData) => {
  for (let arrow in arrowData) {
    for (let i = 0; i < arrowData[arrow].xPoints.length - 1; i++) {
      let x1 = arrowData[arrow].xPoints[i];
      let y1 = arrowData[arrow].yPoints[i];
  
      let x2 = arrowData[arrow].xPoints[i + 1];
      let y2 = arrowData[arrow].yPoints[i + 1];
  
      lineDraw(x1, y1, x2, y2, arrowData[arrow].type, arrowData[arrow].id, arrowData[arrow])
      pointDraw(x1, y1, arrowData[arrow].id, arrowData, arrowData[arrow])
  
      if (i === arrowData[arrow].xPoints.length - 2) {
        headDraw(x1, y1, x2, y2, arrowData[arrow].type, arrowData[arrow].id, arrowData[arrow])
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

const loadArrow = (xPointsArr, yPointsArr, origin, destination, type) => {
  arrowData.push({
    xPoints: xPointsArr.slice(),
    yPoints: yPointsArr.slice(),
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
  drawArrows(arrowData);

})
document.querySelector("#compositionarrow").addEventListener("click", (e) => {
  addArrow(arrowData, document.querySelector("form > select").value, "COMPOSITION", arrowID)
  drawArrows(arrowData);
})
document.querySelector("#implementationarrow").addEventListener("click", (e) => {
  addArrow(arrowData, document.querySelector("form > select").value, "IMPLEMENTATION", arrowID)
  drawArrows(arrowData);
})

drawArrows(arrowData);


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

        lineDraw(x1, y1, x2, y2, selectedArrow.type, selectedArrow.id, arrowData[selectedArrow.id]);
        pointDraw(x1, y1, selectedArrow.id, arrowData[selectedArrow.id]);

        if (i === selectedArrow.xPoints.length - 2) {
          headDraw(x1, y1, x2, y2, selectedArrow.type, selectedArrow.id, arrowData[selectedArrow.id])
        }
      }
    }
  }
})

grid.addEventListener('mouseup', (e) => {

// ARROW DIAGRAM INTERACTION

  const finalX = selectedArrow.xPoints[pointIndex];
  const finalY = selectedArrow.yPoints[pointIndex];

  const previousX = selectedArrow.xPoints[pointIndex - 1];
  const previousY = selectedArrow.yPoints[pointIndex - 1];
  
  for (let diagram of diagrams.data) {
    const externalInfo = diagram.external;

    if ((pointIndex === selectedArrow.xPoints.length - 1) && (selectedArrow.destination === null)) {
      if (((finalX >= externalInfo.xPosition) && (finalX <= externalInfo.xPosition + externalInfo.width)) && 
      ((finalY >= externalInfo.yPosition) && (finalY <= externalInfo.yPosition + externalInfo.height))) {
        
        selectedArrow.destination = diagram.external.id;

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

          if (currentArrowData.destination === externalInfo.id) {
            for (let j = 0; j < (2 * currentArrowData.xPoints.length) - 1; j++) {
              const childArrow = childArrows[0];
              childArrow.remove();
            }
          }
        }

        drawArrows(arrowData)
      }
    } else if ((pointIndex === 0) && (selectedArrow.origin === null)) {
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
              childArrow.remove()
            }
          }
        }
        drawArrows(arrowData)
      }
    }
  }

  dragging = false;
  pointIndex = null;
  selectedArrow = null;
})

// LOAD IN DIAGRAMS

// ADDING NEW DIAGRAMS

const getDiagramByID = (id) => {
  for (let diagram of diagrams.data) {
    if (diagram.external.id === id) {
      return diagram;
    }
  }

  return null;
}

let diagramNum = 0;

const addDiagram = (diagrams, x, y, diagramWidth, type, nameText="", methodsText="", fieldsText="", diagramId = diagramID) => {
  const diagram = document.createElement("div");
  diagram.id = "diagram" + diagramId;
  diagram.classList.add("diagram");

  diagram.style.cssText = `
    width: ${diagramWidth}px;
    display: flex;
    flex-direction: column;
    background-color: rgb(230, 230, 230);
    border: 2px solid black;
    position: absolute;


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
      height: document.querySelector(`#${diagram.id}`).offsetHeight,
      id: diagramID
    }
  }


  const xWithOffset = x ;
  const yWithOffset = y;
  
  diagram.style.left = xWithOffset + 'px'
  diagram.style.top = yWithOffset + 'px'
  diagramID++;

  diagrams.data.push(diagramInfo)

  diagram.addEventListener("contextmenu", (e) => {

    if (!e.shiftKey) {
      return;
    }

    const index = diagrams.data.indexOf(diagramInfo);
  
    if (index !== -1) {
      diagrams.data.splice(index, 1);
    }

    grid.removeChild(diagram);
  })
}

const snap = (offset=false) => {
  const elements = document.querySelectorAll('.diagram');
  elements.forEach((element) => {
    let x = Number(element.style.left.slice(0, -2));
    let y = Number(element.style.top.slice(0, -2));
    const diagram = getDiagramByID(Number(element.id.slice(7)))

  

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

        event.target.style.left = x + 'px'
        event.target.style.top = y + 'px'

        diagram.external.xPosition = x;
        diagram.external.yPosition = y;
      })
  })
}

document.querySelector("#addclass > img").addEventListener("click", (e) => {
  addDiagram(diagrams, ((grid.offsetWidth - (defaultDiagramWidth*scale))/2),
   ((grid.offsetHeight - (20*11*scale))/2), defaultDiagramWidth,
   "CLASSDIAGRAM")
  
  snap(true);
})
document.querySelector("#addinterface > img").addEventListener("click", (e) => {
  addDiagram(diagrams, ((grid.offsetWidth - (defaultDiagramWidth*scale))/2),
  ((grid.offsetHeight -20*6*scale)/2), defaultDiagramWidth,
  "INTERFACEDIAGRAM")


  snap(true);
})
document.querySelector("#addabstractclass > img").addEventListener("click", (e) => {
  addDiagram(diagrams, ((grid.offsetWidth - (defaultDiagramWidth*scale))/2),
  ((grid.offsetHeight - 20*11*scale)/2), defaultDiagramWidth,
  "ABSTRACTCLASSDIAGRAM")


  snap(true);
})
document.querySelector("#addexception > img").addEventListener("click", (e) => {
  addDiagram(diagrams, ((grid.offsetWidth - (defaultDiagramWidth*scale))/2),
  ((grid.offsetHeight - 20*1*scale)/2), defaultDiagramWidth,
  "EXCEPTIONDIAGRAM")

  snap(true);
})

snap();

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

        for (let classDiagram of res.classDiagrams) {
          let fieldsText = "";
          for (let field of classDiagram.fields) {
            if (field !== "") {
              fieldsText += field + "\n";
            }
          }

          let methodsText = "";
          for (let method of classDiagram.methods) {
            if (method !== "") {
              methodsText += method + "\n";
            }
          }

          if (!classDiagram.isAbstract) {
            addDiagram(diagrams, classDiagram.xPosition, classDiagram.yPosition, classDiagram.xSize, "CLASSDIAGRAM", classDiagram.name,
             methodsText, fieldsText, classDiagram.id)
          } else {
            addDiagram(diagrams, classDiagram.xPosition, classDiagram.yPosition, classDiagram.xSize, "ABSTRACTCLASSDIAGRAM", classDiagram.name,
            methodsText, fieldsText, classDiagram.id)
          }
        }

        for (let interfaceDiagram of res.interfaceDiagrams) {

          let methodsText = "";
          for (let method of interfaceDiagram.methods) {
            if (method !== "") {
              methodsText += method + "\n";
            }
          }

          addDiagram(diagrams, interfaceDiagram.xPosition, interfaceDiagram.yPosition, interfaceDiagram.xSize, "INTERFACEDIAGRAM", interfaceDiagram.name,
          methodsText, interfaceDiagram.id)
        }
        
        for (let exceptionDiagram of res.exceptionDiagrams) {

          addDiagram(diagrams, exceptionDiagram.xPosition, exceptionDiagram.yPosition, exceptionDiagram.xSize, "EXCEPTIONDIAGRAM", exceptionDiagram.name, exceptionDiagram.id)
        }

        for (let arrow of res.arrows) {
          loadArrow(arrow.xPoints, arrow.yPoints, arrow.origin, arrow.destination, arrow.arrowType);
        }

      })
      .catch(err => {
      })
}

window.addEventListener("DOMContentLoaded", async () => {
  await loadUML(window.localStorage.getItem("username"), window.localStorage.getItem("projectName"))
  snap(true);
})

// SENDING BACK UML INFO TO THE SERVER

const saveUML = async () => {
  const classes = [];
  const interfaces = [];
  const exceptions = [];


  for (let diagram of diagrams.data) {
    if (diagram.external.type === "CLASSDIAGRAM") {
      classes.push({
        type: "CLASS",
        classId: diagram.external.id,
        className: document.querySelector(`#diagram${diagram.external.id} > .name`).value,
        xPosition: diagram.external.xPosition,
        yPosition: diagram.external.yPosition,
        xSize: diagram.external.width,
        ySize: diagram.external.height,
        isAbstract: false,
        fields: JSON.stringify([...document.querySelector(`#diagram${diagram.external.id} > .fields`).value.split("\n")]),
        methods: JSON.stringify([...document.querySelector(`#diagram${diagram.external.id} > .methods`).value.split("\n")])
      })
    } else if (diagram.external.type === "ABSTRACTCLASSDIAGRAM") {
      
      classes.push({
        type: "ABSTRACTCLASS",
        classId: diagram.external.id,
        className: document.querySelector(`#diagram${diagram.external.id} > .name`).value,
        xPosition: diagram.external.xPosition,
        yPosition: diagram.external.yPosition,
        xSize: diagram.external.width,
        ySize: diagram.external.height,
        isAbstract: true,
        fields: JSON.stringify([...document.querySelector(`#diagram${diagram.external.id} > .fields`).textContent.split("\n")]),
        methods: JSON.stringify([...document.querySelector(`#diagram${diagram.external.id} > .methods`).value.split("\n")])
      })
    } else if (diagram.external.type === "INTERFACEDIAGRAM") {
      
      classes.push({
        type: "INTERFACE",
        classId: diagram.external.id,
        className: document.querySelector(`#diagram${diagram.external.id} > .name`).value,
        xPosition: diagram.external.xPosition,
        yPosition: diagram.external.yPosition,
        xSize: diagram.external.width,
        ySize: diagram.external.height,
        fields: null,
        methods: JSON.stringify([...document.querySelector(`#diagram${diagram.external.id} > .methods`).value.split("\n")])
      })
    } else if (diagram.external.type === "EXCEPTIONDIAGRAM") {
      
      classes.push({
        type: "EXCEPTION",
        classId: diagram.external.id,
        className: document.querySelector(`#diagram${diagram.external.id} > .name`).value,
        xPosition: diagram.external.xPosition,
        yPosition: diagram.external.yPosition,
        xSize: diagram.external.width,
        ySize: diagram.external.height,
        fields: null,
        methods: null
      })
    } 
  }

  const arrows = [];

  for (let arrow of arrowData) {
    arrows.push({
      origin: arrow.origin,
      destination: arrow.destination,
      arrowType: arrow.type,
      arrowId: arrow.id,
      xPoints: JSON.stringify(arrow.xPoints),
      yPoints: JSON.stringify(arrow.yPoints)
    })
  }


  await fetch("/frontend/saveUML", {
    method: "POST",
    headers: {
        "Accept": "application/json",
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        projectName: window.localStorage.getItem("projectName"),
        diagramCount: diagramID,
        arrowCount: arrowID,
        diagrams: JSON.stringify(classes),
        arrows: JSON.stringify(arrows)
    })
})
    .then(res => {
        return res.json();
    })
    .then(res => {
    })
    .catch(err => {
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
        window.location.href = "http://localhost:5069/frontend/homepage/homepage.html"
    })
}

document.querySelector("#menu > form:nth-of-type(1)").addEventListener("submit", (e) => {
  e.preventDefault();
  saveUML();
})

document.querySelector("#homeForm").addEventListener("submit", (e) => {
  e.preventDefault();
  window.localStorage.setItem("projectName", "");
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
    shareModal.close();
})


grid.addEventListener("contextmenu", (e) => {
  e.preventDefault();
})