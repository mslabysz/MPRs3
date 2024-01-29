var pets = [
    { element: document.getElementById('pet1'), x: 50, y: 100, speedX: 1, speedY: 1 },
    { element: document.getElementById('pet2'), x: 200, y: 300, speedX: -1, speedY: 1 },
    { element: document.getElementById('pet3'), x: 500, y: 600, speedX: 1, speedY: -1 }
];
document.getElementById('increase').addEventListener('click', increasePets);
document.getElementById('decrease').addEventListener('click', decreasePets);
var maxPets = 10;
var minPets = 0;

function increasePets() {
    if (pets.length < maxPets) {
        var newPet = {
            element: document.createElement('div'),
            x: Math.random() * window.innerWidth,
            y: Math.random() * window.innerHeight,
            speedX: Math.random() * 2 - 1,
            speedY: Math.random() * 2 - 1
        };
        newPet.element.id = 'pet' + (pets.length + 1);
        newPet.element.className = 'pet';
        document.body.appendChild(newPet.element);
        pets.push(newPet);
    }
}

function decreasePets() {
    if (pets.length > minPets) {
        var petToRemove = pets.pop();
        petToRemove.element.parentNode.removeChild(petToRemove.element);
    }
}
function movePet() {
    var windowWidth = window.innerWidth;
    var windowHeight = window.innerHeight;

    for (var i = 0; i < pets.length; i++) {
        var pet = pets[i];

        pet.x += pet.speedX;
        pet.y += pet.speedY;

        if (pet.x + pet.element.offsetWidth > windowWidth || pet.x < 0) {
            pet.speedX = -pet.speedX;
        }
        if (pet.y + pet.element.offsetHeight > windowHeight || pet.y < 0) {
            pet.speedY = -pet.speedY;
        }
        pet.element.style.left = pet.x + 'px';
        pet.element.style.top = pet.y + 'px';
    }
    window.requestAnimationFrame(movePet);
}
movePet();