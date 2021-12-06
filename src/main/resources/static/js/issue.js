function openBtn (event){
	const modalWindow = document.getElementById("modal_" + event);
	const modalFilter = document.getElementById("filter");
	
	modalWindow.style.display = "block";
	modalFilter.style.display = "block";
}

function closeBtn (event){
	const modalWindow = document.getElementById("modal_" + event);
	const modalFilter = document.getElementById("filter");
	
	modalWindow.style.display = "none";
	modalFilter.style.display = "none";
}