let data = {};

export default {
    setSubject: function (subject) {
        data.subject = subject;
    },
    getSubject: function () {
        return data.subject;
    },
};
