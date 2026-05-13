
//mobile menu toggle
const toggle = document.getElementById('menu-toggle');
const nav = document.getElementById('main-nav');
const path1  = document.getElementById('path1');
const path2  = document.getElementById('path2');
const path3  = document.getElementById('path3');


toggle.addEventListener('click', () => {
    const isOpened = toggle.classList.toggle('open')
    nav.classList.toggle('menu-open', isOpened)

    if (isOpened) {
        path1.setAttribute('d', 'M7 7 L25 25');
        path3.setAttribute('d', 'M7 25 L25 7');
    } else {
        path1.setAttribute('d', 'M4 7h24');
        path3.setAttribute('d', 'M4 25h24');
    }
})