import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtWorkSharedModule } from '../../shared';
import { ArtWorkAdminModule } from '../../admin/admin.module';
import {
    UserProfileValorationService,
    UserProfileValorationPopupService,
    UserProfileValorationComponent,
    UserProfileValorationDetailComponent,
    UserProfileValorationDialogComponent,
    UserProfileValorationPopupComponent,
    UserProfileValorationDeletePopupComponent,
    UserProfileValorationDeleteDialogComponent,
    userProfileValorationRoute,
    userProfileValorationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userProfileValorationRoute,
    ...userProfileValorationPopupRoute,
];

@NgModule({
    imports: [
        ArtWorkSharedModule,
        ArtWorkAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserProfileValorationComponent,
        UserProfileValorationDetailComponent,
        UserProfileValorationDialogComponent,
        UserProfileValorationDeleteDialogComponent,
        UserProfileValorationPopupComponent,
        UserProfileValorationDeletePopupComponent,
    ],
    entryComponents: [
        UserProfileValorationComponent,
        UserProfileValorationDialogComponent,
        UserProfileValorationPopupComponent,
        UserProfileValorationDeleteDialogComponent,
        UserProfileValorationDeletePopupComponent,
    ],
    providers: [
        UserProfileValorationService,
        UserProfileValorationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtWorkUserProfileValorationModule {}
