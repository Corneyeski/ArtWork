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
            console.log(this.account)
            this.userService.getProfileUser(account.id).subscribe((response) => {
                this.userRequest = response;
                console.log(this.userRequest)
                this.mergeUser(this.account, this.userRequest);
            }, () => {
                console.log("error")
            });
        });
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    mergeUser(account, user){
        this.user = {
            "login":                account.login,
            "email":                account.email,
            "name":                 account.firstName,
            "surname":              account.surname,
            "city":                 user.city,
            "image":                user.image,
            "imageContentType":     user.imageContentType,
            "birthdate":            user.birthdate,
            "kind":                 user.king, 
            "phone":                user.phone,
            "profession":           user.profession,
            "working":              user.working,
            "tags":                 user.tags
        }
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
}
