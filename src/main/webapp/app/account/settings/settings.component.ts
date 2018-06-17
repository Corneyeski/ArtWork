import { Component, OnInit } from '@angular/core';
import { JhiLanguageService, JhiDataUtils } from 'ng-jhipster';

import { Principal, AccountService, JhiLanguageHelper, UserService } from '../../shared';

@Component({
    selector: 'jhi-settings',
    templateUrl: './settings.component.html',
    styleUrls: [
        'settings.css'
    ]
})
export class SettingsComponent implements OnInit {
    error: string;
    success: string;
    account: any;
    languages: any[];
    user: any;
    userRequest: any;

    view = "data";
    editProfile:{
        "login":                "",
        "email":                "",
        "name":                 "",
        "surname":              "",
        "city":                 "",
        "image":                "",
        "imageContentType":     "",
        "birthdate":            "",
        "kind":                 "",
        "phone":                "",
        "profession":           "",
        "working":              false,
        "tags":                 any
    }

    constructor(
        private accountService: AccountService,
        private userService: UserService,
        private principal: Principal,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private dataUtils: JhiDataUtils,
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = this.copyAccount(account);
            console.log(account)
            this.userService.getProfileUser(account.id).subscribe((response) => {
                this.userRequest = response;
                console.log(this.userRequest)
                this.mergeUser(this.userRequest);
            }, () => {
                console.log("error")
            });
        });
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    mergeUser(user){
        this.user = {
            "login":                user.login,
            "email":                user.email,
            "name":                 user.firstName,
            "surname":              user.lastName,
            "city":                 user.userExt.city,
            "image":                user.userExt.image,
            "imageContentType":     user.userExt.imageContentType,
            "birthdate":            user.userExt.birthdate,
            "kind":                 user.userExt.king, 
            "phone":                user.userExt.phone,
            "profession":           user.userExt.profession,
            "working":              user.userExt.working,
            "tags":                 user.userExt.tags
        }

        this.editProfile = Object.assign({}, this.user);
        console.log(this.user)
    }

    save() {
        this.account.save(this.account).subscribe(() => {
            this.error = null;
            this.success = 'OK';
            this.principal.identity(true).then((account) => {
                this.account = this.copyAccount(account);
            });
            this.languageService.getCurrent().then((current) => {
                if (this.account.langKey !== current) {
                    this.languageService.changeLanguage(this.account.langKey);
                }
            });
        }, () => {
            this.success = null;
            this.error = 'ERROR';
        });
    }

    copyAccount(account) {
        return {
            activated: account.activated,
            email: account.email,
            firstName: account.firstName,
            langKey: account.langKey,
            lastName: account.lastName,
            login: account.login,
            imageUrl: account.imageUrl
        };
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    changeView(data){
        this.view = data;
    }

    modifyProfile(){
        console.log(this.editProfile)
    }
}
