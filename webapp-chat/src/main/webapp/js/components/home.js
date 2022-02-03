import service from '../service.js';
import store from "../store.js";
import router from "../router.js";

const template = `
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h1>Welcome to the Chat App!</h1>
                <p>Please select a subject to start the chat.</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <select id="subject-dropdown" class="form-control">
                    <option value="" disabled selected>Select a subject</option>
                </select>
            </div>
        </div>
        <div style="row">
            <!-- input for adding new subject -->
            <div class="col-md-12">
                <input id="new-subject-input" class="form-control" type="text" placeholder="Enter new subject">
                <button id="add-subject-button" class="btn btn-primary">Ok</button>
            </div>
        </div>
    </div>
`;

function fetchSubjects($view) {
    service.getSubjects()
        .then(subjects => {
            subjects.forEach(subject => {
                $view.find('#subject-dropdown').append(`<option value="${subject}">${subject}</option>`);
            });
        });
}

export default {
    title: 'Home',
    render: function () {
        let $view = $(template);
        fetchSubjects($view);

        $('#add-subject-button', $view).click(event => {
            let subject = $('#new-subject-input', $view).val();
            store.setSubject(subject);
            router.navigate('/chat');
        });
        // on change of the subject dropdown value
        $view.find('#subject-dropdown').change(function () {
            let subject = $(this).val();
            store.setSubject(subject);
            router.navigate('/chat');
        });
        return $view;
    }
};