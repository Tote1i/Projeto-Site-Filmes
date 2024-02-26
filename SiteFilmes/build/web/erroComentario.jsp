<% String mensagemErro = (String) request.getAttribute("mensagemErro"); %>
<% if (mensagemErro != null && !mensagemErro.isEmpty()) { %>
    <div class="alert alert-danger">
        <%= mensagemErro %>
    </div>
<% } %>
