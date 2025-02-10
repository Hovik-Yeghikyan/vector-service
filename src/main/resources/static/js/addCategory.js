// app.js

const addCategoryForm = document.getElementById('add-category-form');
const categoryNameInput = document.getElementById('category-name');
const categoryImageInput = document.getElementById('category-image');
const imagePreview = document.getElementById('image-preview');
const previewImg = document.getElementById('preview-img');

// Handle file input and show image preview
categoryImageInput.addEventListener('change', function(event) {
    const file = event.target.files[0];

    if (file) {
        const reader = new FileReader();

        reader.onload = function(e) {
            previewImg.src = e.target.result;
            imagePreview.style.display = 'block';
        };

        reader.readAsDataURL(file);
    } else {
        imagePreview.style.display = 'none';
    }
});

// Handle form submission
addCategoryForm.addEventListener('submit', function(event) {
    event.preventDefault();

    const categoryName = categoryNameInput.value;
    const categoryImage = categoryImageInput.files[0];

    if (!categoryImage) {
        alert('Please select an image');
        return;
    }

    const formData = new FormData();
    formData.append('category-name', categoryName);
    formData.append('category-image', categoryImage);

    // You can send formData to a server (using fetch, axios, etc.)
    // Example of sending the data using fetch (uncomment the following lines if needed):

    // fetch('your-server-endpoint', {
    //     method: 'POST',
    //     body: formData
    // })
    // .then(response => response.json())
    // .then(data => {
    //     console.log('Success:', data);
    //     alert('Category added successfully!');
    // })
    // .catch(error => {
    //     console.error('Error:', error);
    //     alert('There was an error adding the category.');
    // });

    // Log the category name and image (for demo purposes)
    console.log('Category Name:', categoryName);
    console.log('Category Image:', categoryImage.name);

    // Clear the form
    addCategoryForm.reset();
    imagePreview.style.display = 'none';
});
