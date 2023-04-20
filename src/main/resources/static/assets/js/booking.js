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

var bookButton = document.querySelector('button.book-now');
var lastScrollTop = 0;

window.addEventListener('scroll', function () {
    var top = window.pageYOffset || document.documentElement.scrollTop;

    if (top > lastScrollTop) {
        bookButton.style.display = 'none';
    } else {
        bookButton.style.display = 'block';
    }

    lastScrollTop = top;
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
        .when("/book-now", {
            templateUrl: "/assets/view/book.html",
            controller: "bookCtrl",
        })
        .otherwise({
            redirectTo: "/"
        });
});

app.controller("bookOnlineCtrl", function ($scope, $http, $location) {

    $scope.form = {}

    $scope.init = function () {
        $http.get("http://localhost:8000/api/booking-online/room-types").then(resp => {
            $scope.roomTypes = resp.data;
        }).catch(error => {
            console.log("Error", error);
        })
        $http.get("http://localhost:8000/api/booking-online/rooms").then(resp => {
            $scope.rooms = resp.data;
        }).catch(error => {
            console.log("Error", error);
        })
        $http.get("http://localhost:8000/api/booking-online/services").then(resp => {
            $scope.services = resp.data;
        }).catch(error => {
            console.log("Error", error);
        })
        $http.get("http://localhost:8000/api/booking-online/promotions").then(resp => {
            $scope.promotions = resp.data;
        }).catch(error => {
            console.log("Error", error);
        })
    }

    $scope.bookNow = function () {
        $location.path("/book-now");
    }

    $scope.loadImageRoom = function (codeRoom) {
        $http.get("http://localhost:8000/api/booking-online/img/" + codeRoom).then(resp => {
            $scope.imageRooms = resp.data;
        }).catch(error => {
            console.log("Error", error);
        })
    }

    $scope.url = function (imageName) {
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

app.controller("bookCtrl", function ($scope, $http, $routeParams, $filter) {

    $scope.formBook = {};
    $scope.formBook.numAdults = 2;
    $scope.formBook.numChildren = 0;
    $scope.formBook.numRoomsBooking = [];
    $scope.infoRoomBooking = [];
    $scope.roomTypes = [];

    // var code = $routeParams.code
    var code = null

    $scope.getRoomType = function () {
        $http.get("/api/booking-online/room-types").then(resp => {
            $scope.roomTypes = resp.data
        }).catch(error => {
            console.log("Error", error);
        });
    }

    $scope.changeDate = function () {
        $scope.infoRoomBooking = [];
    }

    $scope.closeDropdown = function () {
        $('.dropdown-menu').removeClass('show');
    };

    $scope.checkRoom = function () {
        if ($scope.formBook.roomType == null || $scope.formBook.roomType == undefined) {
            $scope.formBook.roomType = "";
        };
        $http.get('/api/booking-online/info', {
            params: {
                checkinDate: $filter('date')($scope.formBook.checkinDate, 'dd-MM-yyyy'),
                checkoutDate: $filter('date')($scope.formBook.checkoutDate, 'dd-MM-yyyy'),
                roomType: $scope.formBook.roomType
            }
        }).then(function (response) {
            $scope.infoRoomBooking = response.data;
            for (var i = 0; i < $scope.infoRoomBooking.length; i++) {
                if ($scope.infoRoomBooking[i].promotion != null) {
                    var percent = $scope.infoRoomBooking[i].promotion.percent;
                    var price = $scope.infoRoomBooking[i].price;
                    var maxDiscount = $scope.infoRoomBooking[i].promotion.maxDiscount;

                    $scope.infoRoomBooking[i].newPrice = price * (100 - percent) / 100;
                    if ((percent / 100 * price) > maxDiscount) {
                        $scope.infoRoomBooking[i].newPrice = price - maxDiscount;
                    }
                }
            }
            if ($scope.infoRoomBooking.length == 0) {
                alert('Không có phòng hợp lệ.');
            }
            console.log($scope.infoRoomBooking);
        }).catch(function (error) {
            console.error('Error fetching data:', error);
        });
    }

    $scope.countDown = function () {

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

    $scope.openModalBooking = function () {
        // if ($scope.infoRoomBooking == null || $scope.infoRoomBooking.length == 0) {
        //     alert("Vui lòng chọn ngày checkin, checkout và kiểm tra lại lượng phòng còn lại!");
        //     return;
        // }
        // if ($scope.infoRoomBooking[0].quantity == 0) {
        //     alert("Phòng đã hết!");
        //     return;
        // }
        $scope.countDown();
        $('#book-modal').modal('show');
    }

    $scope.showDetailRoomType = function (roomType) {
        $scope.currentRoomType = roomType;
        console.log(roomType);
        $('#room-type-modal').modal('show');
    };

    $scope.updateTotalNumRooms = function () {
        $scope.formBook.numRoomsBooking = [];
        for (var i = 0; i < $scope.infoRoomBooking.length; i++) {
            var roomType = $scope.infoRoomBooking[i].name;
            var numRooms = $scope.infoRoomBooking[i].numRooms || 0;
            if (numRooms > 0) {
                $scope.formBook.numRoomsBooking.push({
                    roomType: roomType,
                    numRooms: numRooms
                });
            }
        }
        console.log($scope.formBook.numRoomsBooking);
    };

    $scope.getBooking = function () {
        console.log($scope.formBook);

        $scope.formBook.checkinDate = $filter('date')($scope.formBook.checkinDate, 'dd-MM-yyyy');
        $scope.formBook.checkoutDate = $filter('date')($scope.formBook.checkoutDate, 'dd-MM-yyyy');

        var bookingJson = JSON.stringify($scope.formBook);

        console.log(bookingJson);

        $http.post("/api/booking-online/get-booking", $scope.formBook).then(resp => {
            console.log(resp.data);
            //window.location.href = "/booking-online";
        }).catch(error => {
            console.log("Error", error);
        });
    }

    $scope.init = function () {
        $scope.getRoomType();
    }

    $scope.init()

});