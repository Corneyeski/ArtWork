import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtWorkSharedModule } from '../../shared';
import { ArtWorkAdminModule } from '../../admin/admin.module';
import {
    MultimediaService,
    MultimediaPopupService,
    MultimediaComponent,
    MultimediaDetailComponent,
    MultimediaDialogComponent,
    MultimediaPopupComponent,
    MultimediaDeletePopupComponent,
    MultimediaDeleteDialogComponent,
    multimediaRoute,
    multimediaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...multimediaRoute,
    ...multimediaPopupRoute,
];

@NgModule({
    imports: [
        ArtWorkSharedModule,
        ArtWorkAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MultimediaComponent,
        MultimediaDetailComponent,
        MultimediaDialogComponent,
        MultimediaDeleteDialogComponent,
        MultimediaPopupComponent,
        MultimediaDeletePopupComponent,
    ],
    entryComponents: [
        MultimediaComponent,
        MultimediaDialogComponent,
        MultimediaPopupComponent,
        MultimediaDeleteDialogComponent,
        MultimediaDeletePopupComponent,
    ],
    providers: [
        MultimediaService,
        MultimediaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtWorkMultimediaModule {}
