import service from '../service.js';
import router from '../router.js';

// You can navigate e.g. to http://localhost:8080/#/company/1

// component template as table rows
const template = `
    <div>
        <h1>Company</h1> 
        <table>
			<thead>
				<tr>
					<th>Name</th>
					<th style="font-weight: normal" id="name"></th>
				</tr>
				<tr>
					<th>Address</th>
					<th style="font-weight: normal" id="address"></th>
                </tr>
				<tr>
					<th>URL</th>
                    <th style="font-weight: normal" id="url"></th>
                </tr>
                <tr>
					<th>CEO</th>
                    <th style="font-weight: normal" id="ceo"></th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
		<a id="homeRef" style="cursor: pointer">Home</a>
    </div>
`;

function fetchCompany(companyId,$view) {
    return service.getCompany(companyId)
        .then(company => {
            // apend company to table
            $view.find('#name').text(company.name);
            $view.find('#address').html(`
                ${company.address.street}</br>
                ${company.address.postalCode} ${company.address.city}</br>
                ${company.address.country}
                
            `
            );
            $view.find('#url').text(company.url);
            $view.find('#ceo').text(company.ceo);


        });
}

function getCompanyId() {
    // get company id from url /company/:id
    const url = window.location.href;
    const id = url.split('/').pop();
    return id;
}

export default {
    title: 'Company',
    render: function() {
        let $view = $(template)
        let companyId = getCompanyId();
        fetchCompany(companyId,$view);
        $view.find('#homeRef').click(() => {
            router.navigate('/');
        });
        return $view;
    }
};