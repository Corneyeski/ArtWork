import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ArtWorkUserExtModule } from './user-ext/user-ext.module';
import { ArtWorkToolModule } from './tool/tool.module';
import { ArtWorkResumeCreationModule } from './resume-creation/resume-creation.module';
import { ArtWorkLanguageModule } from './language/language.module';
import { ArtWorkTrainingModule } from './training/training.module';
import { ArtWorkExperienceModule } from './experience/experience.module';
import { ArtWorkPricesTModule } from './prices-t/prices-t.module';
import { ArtWorkConnectionModule } from './connection/connection.module';
import { ArtWorkFollowingModule } from './following/following.module';
import { ArtWorkOfferModule } from './offer/offer.module';
import { ArtWorkMultimediaModule } from './multimedia/multimedia.module';
import { ArtWorkAlbumModule } from './album/album.module';
import { ArtWorkValorationModule } from './valoration/valoration.module';
import { ArtWorkConversationModule } from './conversation/conversation.module';
import { ArtWorkMessageModule } from './message/message.module';
import { ArtWorkUserProfileValorationModule } from './user-profile-valoration/user-profile-valoration.module';
import { ArtWorkReportModule } from './report/report.module';
import { ArtWorkReasonReportModule } from './reason-report/reason-report.module';
import { ArtWorkBlockedModule } from './blocked/blocked.module';
import { ArtWorkCommentLikeModule } from './comment-like/comment-like.module';
import { ArtWorkCommentModule } from './comment/comment.module';
import { ArtWorkCityModule } from './city/city.module';
import { ArtWorkCountryModule } from './country/country.module';
import { ArtWorkProfessionModule } from './profession/profession.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ArtWorkUserExtModule,
        ArtWorkToolModule,
        ArtWorkResumeCreationModule,
        ArtWorkLanguageModule,
        ArtWorkTrainingModule,
        ArtWorkExperienceModule,
        ArtWorkPricesTModule,
        ArtWorkConnectionModule,
        ArtWorkFollowingModule,
        ArtWorkOfferModule,
        ArtWorkMultimediaModule,
        ArtWorkAlbumModule,
        ArtWorkValorationModule,
        ArtWorkConversationModule,
        ArtWorkMessageModule,
        ArtWorkUserProfileValorationModule,
        ArtWorkReportModule,
        ArtWorkReasonReportModule,
        ArtWorkBlockedModule,
        ArtWorkCommentLikeModule,
        ArtWorkCommentModule,
        ArtWorkCityModule,
        ArtWorkCountryModule,
        ArtWorkProfessionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtWorkEntityModule {}
