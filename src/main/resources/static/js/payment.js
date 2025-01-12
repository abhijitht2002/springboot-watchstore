function togglePaymentSection(method) {

	const codSection = document.getElementById('cod-section')
	const cardSection = document.getElementById('card-section')

	if (method === 'cod') {

		codSection.style.display = 'block';
		cardSection.style.display = 'none';
	}else if (method === 'card') {

		cardSection.style.display = 'block';
		codSection.style.display = 'none';
	}
}



