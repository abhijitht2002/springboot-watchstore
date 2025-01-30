let countdown = 5;

const countdownElement = document.getElementById('countdown');

const timer = setInterval(  ()  =>  {

    countdown--;
    countdownElement.textContent = countdown;

    if(countdown === 0){

        clearInterval(timer);
        window.location.href = '/';
    }

}, 1000);