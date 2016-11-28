$(document).ready(function() {
	registerSearch();
	registerTemplate();
});

function registerSearch() {
	$("#search").submit(function(ev){
		event.preventDefault();
		var search = $('#q').val();
		var q = search.replace(/\w+:\w+/g, '').replace(/ +/g, ' ');
        var data = { q: q };
        // Add all the commands to the data object (command:parameter)
		var matches = search.match(/\w+:\w+/g);
		if (matches != null) {
			matches.forEach(function(c) {
                var tokens = c.split(':');
                data[tokens[0]] = tokens[1];
            });
        }
		$.get($(this).attr('action'), data, function(res) {
			$("#resultsBlock").html(Mustache.render(template, res));
		});	
	});
}

function registerTemplate() {
	template = $("#template").html();
	Mustache.parse(template);
}
