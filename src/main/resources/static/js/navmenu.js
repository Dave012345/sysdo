function openMenu(){
	var menu = document.getElementsByClassName("menu");

	var i;
	for (i = 0; i < menu.length; i++){

		menu[i].addEventListener("click", function() {
			this.classList.toggle("active");
			var mobile = this.nextElementSibling;

			if(mobile.style.display === "block"){
				mobile.style.display = "none";
			} else {
				mobile.style.display = "block";
			}
		});
	}
}

function pagination(){
    var url = new URLSearchParams(document.location.search.substring());
    var id = url.get("page");
    if (id === null) id = "0";

    document.getElementById(id).style.color = 'orangered';
}
