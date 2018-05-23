$(document).ready(function(){
            var content;
        	$.get("http://127.0.0.1:8080/Rss/feed", function(data){
            	content= data;
        		console.log(content);
        
         		$.each(data, function () {
                  var node = document.createElement("LI");
                  
                  var currentData = this;
                  node.onclick = function(mouseEvent){
                  	  var titleDiv = document.getElementById("title");
                      titleDiv.innerHTML = currentData.title;
                      var contentDiv = document.getElementById("content");
                      contentDiv.innerHTML = currentData.body;
                      var linkA = document.getElementById("readMore");
                      linkA.href = currentData.link;
					  linkA.style.display = "block";
                  };
                  
                  var textnode = document.createTextNode(this.title);
                  node.appendChild(textnode);
                  document.getElementById("linkList").appendChild(node);	
				});
    		});
});