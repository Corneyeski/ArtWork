import { Component, OnInit, ElementRef } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiBase64Service, JhiDataUtils, JhiLanguageService } from 'ng-jhipster';

import { Account, LoginModalService, Principal, LoginService, AuthServerProvider } from '../shared';
import { MultimediaService } from '../entities/multimedia'
import { UserExtService } from '../entities/user-ext'
import { UserExt } from '../entities/user-ext/user-ext.model'
import { Register } from '../account/register/register.service'
import { Http } from '@angular/http';

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

    loginError = false;
    registerSuccess = false;
    registerError = false;

    userExt: UserExt;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private userExtService: UserExtService,
        private multimediaService: MultimediaService,
        private dataUtils: JhiDataUtils,
        private languageService: JhiLanguageService,
        private registerService: Register,
        private http: Http,
        private elementRef: ElementRef
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
            //this.getMultimedia();
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
                //this.getMultimedia();
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
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
            //this.getMultimedia();

        }, (err) => {
            console.log("ERROR")
            this.loginError = true;
        });

        this.password = '';
    }

    register(){
        this.registerCredentials.newUserExtDTO = {
            birthdate: new Date(1527869120),
            kind: 1,
            image: this.registerCredentials.image,
            imageContentType: this.registerCredentials.imageContentType,
            working: false
        };

        if (this.registerCredentials.password !== this.registerCredentials.confirmPassword) {
            console.log("NO COINCIDEN")
            this.passwordNoMatch = true;
        } else {
            this.passwordNoMatch = false;
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

    selectImg(){
        document.getElementById("file_image").click();        
    }

    /*getMultimedia(){
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
    }*/

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.registerCredentials, this.elementRef, field, fieldContentType, idInput);
    }

}
