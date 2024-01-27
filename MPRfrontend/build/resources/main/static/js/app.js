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
            x: Math.random() * window.innerWidth, // Random x-coordinate within the window's width
            y: Math.random() * window.innerHeight, // Random y-coordinate within the window's height
            speedX: Math.random() * 2 - 1, // Random speed between -1 and 1
            speedY: Math.random() * 2 - 1  // Random speed between -1 and 1
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
    // Get the dimensions of the window
    var windowWidth = window.innerWidth;
    var windowHeight = window.innerHeight;

    // Update the position of each pet
    for (var i = 0; i < pets.length; i++) {
        var pet = pets[i];

        // Calculate the new position of the pet
        pet.x += pet.speedX;
        pet.y += pet.speedY;

        // Check if the pet has reached the edge of the window
        if (pet.x + pet.element.offsetWidth > windowWidth || pet.x < 0) {
            pet.speedX = -pet.speedX; // Reverse the horizontal direction
        }
        if (pet.y + pet.element.offsetHeight > windowHeight || pet.y < 0) {
            pet.speedY = -pet.speedY; // Reverse the vertical direction
        }

        // Update the position of the pet
        pet.element.style.left = pet.x + 'px';
        pet.element.style.top = pet.y + 'px';
    }

    // Request the next frame
    window.requestAnimationFrame(movePet);
}

// Start the animation
movePet();