/*!
* A javascript snippet used for 
* calling the reply button request to 
* post a new comment. 
*/
$(document).ready(function() { 
    $(".replyButton").each(function() {
        var button = this;
        $(button).click(function() {
            alert($(button).attr("id"));
            // code to execute when the user clicks on a reply button
        });
    });
});