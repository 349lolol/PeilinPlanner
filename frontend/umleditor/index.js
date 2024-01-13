const grid = document.getElementById('grid');

grid.addEventListener("scroll", (event) => {
  console.log(event);

  for (const child of grid.children) {
    grid.style.scale = "";
  }
})

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
          relativePoints: [ { x: 0, y: 0 } ]
        }),
        interact.modifiers.restrict({
          restriction: element.parentNode,
          elementRect: { top: 0, left: 0, bottom: 1, right: 1 },
          endOnly: true
        })
      ],
      inertia: true
    })
    .on('dragmove', function (event) {
      x += event.dx
      y += event.dy

      event.target.style.transform = 'translate(' + x + 'px, ' + y + 'px)'
    })
})