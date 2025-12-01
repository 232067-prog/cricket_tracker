// Mobile Menu Toggle
document.addEventListener('DOMContentLoaded', function () {
    const menuToggle = document.querySelector('.menu-toggle');
    const navbarMenu = document.querySelector('.navbar-menu');

    if (menuToggle) {
        menuToggle.addEventListener('click', function () {
            navbarMenu.classList.toggle('active');
        });
    }

    // Close mobile menu when clicking outside
    document.addEventListener('click', function (event) {
        if (!event.target.closest('.navbar-container')) {
            navbarMenu?.classList.remove('active');
        }
    });

    // Set active nav link
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.navbar-menu a');
    navLinks.forEach(link => {
        if (link.getAttribute('href') === currentPath ||
            (currentPath.includes(link.getAttribute('href')) && link.getAttribute('href') !== '/')) {
            link.classList.add('active');
        }
    });
});

// Delete Confirmation Modal
function showDeleteModal(matchId, opponentName, matchDate) {
    const modal = document.getElementById('deleteModal');
    const modalBody = document.getElementById('deleteModalBody');
    const confirmBtn = document.getElementById('confirmDeleteBtn');

    modalBody.innerHTML = `Are you sure you want to delete the match against <strong>${opponentName}</strong> on <strong>${matchDate}</strong>? This action cannot be undone.`;

    confirmBtn.onclick = function () {
        document.getElementById('deleteForm' + matchId).submit();
    };

    modal.classList.add('active');
}

function closeDeleteModal() {
    const modal = document.getElementById('deleteModal');
    modal.classList.remove('active');
}

// Close modal when clicking outside
window.addEventListener('click', function (event) {
    const modal = document.getElementById('deleteModal');
    if (event.target === modal) {
        closeDeleteModal();
    }
});

// Form Validation
function validateMatchForm(form) {
    let isValid = true;
    const errors = [];

    // Get form values
    const matchDate = form.querySelector('[name="matchDate"]').value;
    const opponentName = form.querySelector('[name="opponentName"]').value;
    const runsScored = parseInt(form.querySelector('[name="runsScored"]').value) || 0;
    const ballsFaced = parseInt(form.querySelector('[name="ballsFaced"]').value) || 0;
    const wicketsTaken = parseInt(form.querySelector('[name="wicketsTaken"]').value) || 0;
    const oversBowled = parseFloat(form.querySelector('[name="oversBowled"]').value) || 0;
    const runsConceded = parseInt(form.querySelector('[name="runsConceded"]').value) || 0;
    const catches = parseInt(form.querySelector('[name="catches"]').value) || 0;

    // Validate match date
    if (!matchDate) {
        errors.push('Match date is required');
        isValid = false;
    } else {
        const selectedDate = new Date(matchDate);
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        if (selectedDate > today) {
            errors.push('Match date cannot be in the future');
            isValid = false;
        }
    }

    // Validate opponent name
    if (!opponentName || opponentName.trim().length < 2) {
        errors.push('Opponent name must be at least 2 characters');
        isValid = false;
    }

    // Validate numeric fields
    if (runsScored < 0) {
        errors.push('Runs scored cannot be negative');
        isValid = false;
    }

    if (ballsFaced < 0) {
        errors.push('Balls faced cannot be negative');
        isValid = false;
    }

    if (wicketsTaken < 0) {
        errors.push('Wickets taken cannot be negative');
        isValid = false;
    }

    if (oversBowled < 0) {
        errors.push('Overs bowled cannot be negative');
        isValid = false;
    }

    if (runsConceded < 0) {
        errors.push('Runs conceded cannot be negative');
        isValid = false;
    }

    if (catches < 0) {
        errors.push('Catches cannot be negative');
        isValid = false;
    }

    // Show errors if any
    if (!isValid) {
        alert('Please fix the following errors:\n\n' + errors.join('\n'));
    }

    return isValid;
}

// Auto-dismiss alerts after 5 seconds
document.addEventListener('DOMContentLoaded', function () {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.animation = 'fadeOut 0.5s ease';
            setTimeout(() => {
                alert.remove();
            }, 500);
        }, 5000);
    });
});

// Add fadeOut animation
const style = document.createElement('style');
style.textContent = `
    @keyframes fadeOut {
        from {
            opacity: 1;
            transform: translateY(0);
        }
        to {
            opacity: 0;
            transform: translateY(-20px);
        }
    }
`;
document.head.appendChild(style);

// Number formatting
function formatNumber(num, decimals = 2) {
    if (num === null || num === undefined) return '0';
    return parseFloat(num).toFixed(decimals);
}

// Smooth scroll to top
function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}

// Add scroll to top button
document.addEventListener('DOMContentLoaded', function () {
    const scrollBtn = document.createElement('button');
    scrollBtn.innerHTML = '↑';
    scrollBtn.className = 'scroll-to-top';
    scrollBtn.style.cssText = `
        position: fixed;
        bottom: 2rem;
        right: 2rem;
        width: 50px;
        height: 50px;
        border-radius: 50%;
        background: var(--gradient-primary);
        color: white;
        border: none;
        font-size: 1.5rem;
        cursor: pointer;
        opacity: 0;
        transition: opacity 0.3s ease, transform 0.3s ease;
        z-index: 1000;
        box-shadow: var(--shadow-lg);
    `;

    document.body.appendChild(scrollBtn);

    window.addEventListener('scroll', function () {
        if (window.scrollY > 300) {
            scrollBtn.style.opacity = '1';
        } else {
            scrollBtn.style.opacity = '0';
        }
    });

    scrollBtn.addEventListener('click', scrollToTop);

    scrollBtn.addEventListener('mouseenter', function () {
        this.style.transform = 'translateY(-5px)';
    });

    scrollBtn.addEventListener('mouseleave', function () {
        this.style.transform = 'translateY(0)';
    });
});
