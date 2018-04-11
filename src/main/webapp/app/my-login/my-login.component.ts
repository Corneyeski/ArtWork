import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-my-login',
  templateUrl: './my-login.component.html',
  styles: []
})
export class MyLoginComponent implements OnInit {

  username:string;
  password:string;
  authenticationError:boolean = false;


  constructor() { }

  ngOnInit() {
  }

  login(){
    console.log("login into Artwork")
    console.log(this.username);
    console.log(this.password)
  }
}
