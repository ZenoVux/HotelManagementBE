

window.addEventListener('DOMContentLoaded', event => {

    // Navbar shrink function
    var navbarShrink = function () {
        const navbarCollapsible = document.body.querySelector('#mainNav');
        if (!navbarCollapsible) {
            return;
        }
        if (window.scrollY === 0) {
            navbarCollapsible.classList.remove('navbar-shrink')
        } else {
            navbarCollapsible.classList.add('navbar-shrink')
        }

    };

    // Shrink the navbar 
    navbarShrink();

    // Shrink the navbar when page is scrolled
    document.addEventListener('scroll', navbarShrink);

    //  Activate Bootstrap scrollspy on the main nav element
    const mainNav = document.body.querySelector('#mainNav');
    if (mainNav) {
        new bootstrap.ScrollSpy(document.body, {
            target: '#mainNav',
            rootMargin: '0px 0px -40%',
        });
    };

    // Collapse responsive navbar when toggler is visible
    const navbarToggler = document.body.querySelector('.navbar-toggler');
    const responsiveNavItems = [].slice.call(
        document.querySelectorAll('#navbarResponsive .nav-link')
    );
    responsiveNavItems.map(function (responsiveNavItem) {
        responsiveNavItem.addEventListener('click', () => {
            if (window.getComputedStyle(navbarToggler).display !== 'none') {
                navbarToggler.click();
            }
        });
    });

});

var app = angular.module("myApp", ["ngRoute"]);

app.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "/assets/view/home.html",
            controller: "bookOnlineCtrl",
        })
        .when("/book/:code", {
            templateUrl: "/assets/view/book.html",
            controller: "bookCtrl",
        })
})


app.controller("bookOnlineCtrl", function ($scope, $http, $location) {
    $scope.form = {}
    $scope.init = function () {
        $http.get("http://localhost:8000/api/booking-online/room-types").then(resp => {
            $scope.roomTypes = resp.data;
            // console.log($scope.roomTypes)
        }).catch(error => {
            console.log("Error", error);
        })
        $http.get("http://localhost:8000/api/booking-online/rooms").then(resp => {
            $scope.rooms = resp.data;
            // console.log($scope.rooms)
        }).catch(error => {
            console.log("Error", error);
        })
        $http.get("http://localhost:8000/api/booking-online/services").then(resp => {
            $scope.services = resp.data;
            // console.log($scope.services)
        }).catch(error => {
            console.log("Error", error);
        })

        $http.get("http://localhost:8000/api/booking-online/promotions").then(resp => {
            $scope.promotions = resp.data;
            // console.log($scope.promotions)
        }).catch(error => {
            console.log("Error", error);
        })

    }


    $scope.loadImageRoom = function (codeRoom) { // load anh phong
        $http.get("http://localhost:8000/api/booking-online/img/" + codeRoom).then(resp => {
            $scope.imageRooms = resp.data;
            // console.log(resp.data)
        }).catch(error => {
            console.log("Error", error);
        })
    }

    $scope.url = function(imageName){
        return `http://localhost:8000/images/${imageName}`;
}

    $scope.close = function () {
        var myModalEl = document.getElementById('roomModal1');
        var modal = bootstrap.Modal.getInstance(myModalEl)
        modal.hide();
    }

    $scope.views = function (roomType, code) {
        $scope.loadImageRoom(code)

        // $scope.link = link;
        $scope.form = roomType;
    }
    $scope.books = function (roomTypeCode) {
        // $scope.close();
        $location.path("/book/" + roomTypeCode);
    }

    $scope.init();
});

app.controller("bookCtrl", function ($scope, $http, $routeParams) {
    $scope.formBook={};
    var code = $routeParams.code

    $scope.init = function(){
    $scope.checkin=document.getElementById("myDate").value;
    $scope.checkout=document.getElementById("myDate1").value;
    $scope.formBook ={
        checkin:$scope.checkin,
        checkout:$scope.checkout,
    }
    }


    
    $http.get("http://localhost:8000/api/booking-online/room-types/" + code).then(resp => {
        $scope.form = resp.data
    }).catch(error => {
        console.log("Error", error);
    })


    paypal.Buttons({ // vi du phuong thuc thanh toan
        style: {
            label: 'checkout',
            size: 'medium', // small | medium | large | responsive
            shape: 'pill', // pill | rect
            color: 'gold', // gold | blue | silver | black,
            layout: 'vertical'
        },

    }).render('#paypal-button-container')




    $scope.countDown = function () {
        alert("Hay chon ngay checkin-out de kiem tra lương phòng còn lại")
        var countdownElement = document.getElementById('countdown');

        var startTime = new Date().getTime();
        var endTime = startTime + 10 * 60 * 1000; // giu phong 10p

        var countdownInterval = setInterval(function () {
            var timeLeft = endTime - new Date().getTime();

            if (timeLeft <= 0) {
                clearInterval(countdownInterval);
                countdownElement.innerHTML = 'Hết thời gian!';
                return;
            }

            var minutes = Math.floor((timeLeft / (1000 * 60)) % 60);
            var seconds = Math.floor((timeLeft / 1000) % 60);

            var formattedTime = ('0' + minutes).slice(-2) + ':' + ('0' + seconds).slice(-2) + ' phút';
            countdownElement.innerHTML = 'Phòng được giữ trong: ' + formattedTime;
        }, 1000);

    }

    $scope.submit = function () {
        alert("check đầu ra ở console.log")
        console.log($scope.formBook)
    }

$scope.init()

});