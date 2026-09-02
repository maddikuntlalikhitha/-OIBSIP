let taskInput = document.querySelector("#taskInput");

let addBtn = document.querySelector("#addBtn");

let pendingTasks = document.querySelector("#pendingTasks");

let completedTasks = document.querySelector("#completedTasks");

let pendingCount = document.querySelector("#pendingCount");

let completedCount = document.querySelector("#completedCount");

let pendingEmpty = document.querySelector("#pendingEmpty");

let completedEmpty = document.querySelector("#completedEmpty");



let tasks = JSON.parse(localStorage.getItem("tasks")) || [];




displayTasks();



addBtn.addEventListener("click", addTask);


function addTask() {

    let taskText = taskInput.value.trim();


    if (taskText === "") {

        alert("Please enter a task");

        return;
    }


    let task = {

        id: Date.now(),

        text: taskText,

        completed: false,

        createdAt: new Date().toLocaleString()
    };


    tasks.push(task);


    saveTasks();


    displayTasks();


    taskInput.value = "";
}



function displayTasks() {

    pendingTasks.innerHTML = "";

    completedTasks.innerHTML = "";


    let pending = tasks.filter(function(task) {

        return task.completed === false;

    });


    let completed = tasks.filter(function(task) {

        return task.completed === true;

    });


    

    pending.forEach(function(task) {

        createTaskElement(task);

    });


    

    completed.forEach(function(task) {

        createTaskElement(task);

    });


    

    pendingCount.textContent =
        pending.length + " Pending";


    completedCount.textContent =
        completed.length + " Completed";


    

    if (pending.length === 0) {

        pendingEmpty.style.display = "block";

    } else {

        pendingEmpty.style.display = "none";

    }


    if (completed.length === 0) {

        completedEmpty.style.display = "block";

    } else {

        completedEmpty.style.display = "none";

    }

}




function createTaskElement(task) {

    let taskDiv = document.createElement("div");

    taskDiv.classList.add("task");


    if (task.completed) {

        taskDiv.classList.add("completed");

    }


    taskDiv.innerHTML = `

        <div class="task-info">

            <p class="task-text">

                ${task.text}

            </p>


            <p class="time">

                Added: ${task.createdAt}

            </p>

        </div>


        <div class="task-buttons">

            ${task.completed ? "" :

            `<button class="complete-btn"
            onclick="completeTask(${task.id})">

            Complete

            </button>`}


            <button class="edit-btn"
            onclick="editTask(${task.id})">

            Edit

            </button>


            <button class="delete-btn"
            onclick="deleteTask(${task.id})">

            Delete

            </button>

        </div>

    `;


    if (task.completed) {

        completedTasks.appendChild(taskDiv);

    } else {

        pendingTasks.appendChild(taskDiv);

    }

}




function completeTask(id) {

    tasks = tasks.map(function(task) {

        if (task.id === id) {

            task.completed = true;

            task.completedAt =
                new Date().toLocaleString();

        }

        return task;

    });


    saveTasks();


    displayTasks();

}




function editTask(id) {

    let task = tasks.find(function(task) {

        return task.id === id;

    });


    let newText = prompt(

        "Edit your task:",

        task.text

    );


    if (newText !== null &&
        newText.trim() !== "") {

        task.text = newText;


        saveTasks();


        displayTasks();

    }

}




function deleteTask(id) {

    tasks = tasks.filter(function(task) {

        return task.id !== id;

    });


    saveTasks();


    displayTasks();

}




function saveTasks() {

    localStorage.setItem(

        "tasks",

        JSON.stringify(tasks)

    );

}