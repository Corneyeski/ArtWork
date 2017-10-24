import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtWorkSharedModule } from '../../shared';
import { ArtWorkAdminModule } from '../../admin/admin.module';
import {
    FollowingService,
    FollowingPopupService,
    FollowingComponent,
    FollowingDetailComponent,
    FollowingDialogComponent,
    FollowingPopupComponent,
    FollowingDeletePopupComponent,
    FollowingDeleteDialogComponent,
    followingRoute,
    followingPopupRoute,
} from './';

const ENTITY_STATES = [
    ...followingRoute,
    ...followingPopupRoute,
];

@NgModule({
    imports: [
        ArtWorkSharedModule,
        ArtWorkAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FollowingComponent,
        FollowingDetailComponent,
        FollowingDialogComponent,
        FollowingDeleteDialogComponent,
        FollowingPopupComponent,
        FollowingDeletePopupComponent,
    ],
    entryComponents: [
        FollowingComponent,
        FollowingDialogComponent,
        FollowingPopupComponent,
        FollowingDeleteDialogComponent,
        FollowingDeletePopupComponent,
    ],
    providers: [
        FollowingService,
        FollowingPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtWorkFollowingModule {}
