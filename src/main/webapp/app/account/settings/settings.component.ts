import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

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

    constructor(
        private accountService: AccountService,
        private userService: UserService,
        private principal: Principal,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = this.copyAccount(account);
            this.userService.getProfileUser(account.id).subscribe((response) => {
                this.user = response;
            }, () => {
                console.log("error")
            });
        });
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });


        
        
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
}
