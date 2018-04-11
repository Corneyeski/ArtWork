import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiBase64Service, JhiDataUtils, JhiLanguageService } from 'ng-jhipster';

import { Account, LoginModalService, Principal, LoginService, AuthServerProvider } from '../shared';
import { MultimediaService } from '../entities/multimedia'
import { Register } from '../account/register/register.service'
import { Http } from '@angular/http/src/http';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    username:string;
    password:string;
    loginCredentials:any = {};
    multimedias = [];

    registerCredentials:any = {};
    passwordNoMatch = false;

    registerSuccess = false;
    registerError = false;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager, 
        private loginService: LoginService,
        private multimediaService: MultimediaService,
        private dataUtils: JhiDataUtils,
        private languageService: JhiLanguageService,
        private registerService: Register
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
                this.getMultimedia();
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        console.log(this.username)
        this.loginCredentials.username = this.username;
        this.loginCredentials.password = this.password;
    
        this.loginService.login({
            username: this.username,
            password: this.password,
        }).then(() => {
            console.debug("login OK");
            this.eventManager.broadcast({
                name: 'authenticationSuccess',
                content: 'Sending Authentication Success'
            });
            this.getMultimedia();

        }).catch(() => {
            console.debug("login FAIL");
        });
    }

    register(){
        if (this.registerCredentials.password !== this.registerCredentials.confirmPassword) {
            console.log("NO COINCIDEN")
            this.passwordNoMatch = true;
        } else {
            this.passwordNoMatch = false;
            //this.doNotMatch = null;
            //this.error = null;
            //this.errorUserExists = null;
            //this.errorEmailExists = null;
            this.languageService.getCurrent().then((key) => {
                this.registerCredentials.langKey = key;
                this.registerService.save(this.registerCredentials).subscribe((response) => {
                    console.log(response)
                    if(response.status == 200 || response.status == 201){
                        console.debug("Se ha creado el usuario")
                        this.registerSuccess = true;
                        this.registerError = false;
                    }else{
                        this.registerError = true;
                        this.registerSuccess = false;
                    }
                });
            });
        }
    }

    getMultimedia(){
        this.multimediaService.query({
        }).subscribe(
            response => {
                console.log(response)
                var data = response.json;
                for (let i = 0; i < data.length; i++) {
                    this.multimedias.push(data[i]);
                }
            }
        );
    }
    openFile(contentType, field) {
        console.log("TODO -> ABRIR MODAL CON LA IMG")
        //return this.dataUtils.openFile(contentType, field);
    }
}
