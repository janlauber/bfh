import router from './router.js';
import quotesComp from './components/quotes.js';
import company from './components/company.js';

router.register('/', quotesComp);
router.register('/company', company);

if (location.hash) {
	let path = location.hash.replace('#', '');
	router.navigate(path);
} else router.navigate('/');
