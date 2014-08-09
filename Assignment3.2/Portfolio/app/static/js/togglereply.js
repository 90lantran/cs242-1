/*!
* Javascript to toggle the appearance 
* or absence of the reply button on 
* the web page.
*/
function Toggle(id) {
	var el = document.getElementById("ToggleTarget" + id);
	if (el.style.display == "block") {
		el.style.display = "none";
	}
	else {
		el.style.display = "block";
	}
}