import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SettingsComponent } from './settings.component';

export const settingsRoute: Route = {
    path: 'account',
    component: SettingsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'global.menu.account.account'
    },
    canActivate: [UserRouteAccessService]
};
