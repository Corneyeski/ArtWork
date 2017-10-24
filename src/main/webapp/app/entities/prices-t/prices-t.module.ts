import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtWorkSharedModule } from '../../shared';
import {
    PricesTService,
    PricesTPopupService,
    PricesTComponent,
    PricesTDetailComponent,
    PricesTDialogComponent,
    PricesTPopupComponent,
    PricesTDeletePopupComponent,
    PricesTDeleteDialogComponent,
    pricesTRoute,
    pricesTPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pricesTRoute,
    ...pricesTPopupRoute,
];

@NgModule({
    imports: [
        ArtWorkSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PricesTComponent,
        PricesTDetailComponent,
        PricesTDialogComponent,
        PricesTDeleteDialogComponent,
        PricesTPopupComponent,
        PricesTDeletePopupComponent,
    ],
    entryComponents: [
        PricesTComponent,
        PricesTDialogComponent,
        PricesTPopupComponent,
        PricesTDeleteDialogComponent,
        PricesTDeletePopupComponent,
    ],
    providers: [
        PricesTService,
        PricesTPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtWorkPricesTModule {}
