/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserProfileValorationDetailComponent } from '../../../../../../main/webapp/app/entities/user-profile-valoration/user-profile-valoration-detail.component';
import { UserProfileValorationService } from '../../../../../../main/webapp/app/entities/user-profile-valoration/user-profile-valoration.service';
import { UserProfileValoration } from '../../../../../../main/webapp/app/entities/user-profile-valoration/user-profile-valoration.model';

describe('Component Tests', () => {

    describe('UserProfileValoration Management Detail Component', () => {
        let comp: UserProfileValorationDetailComponent;
        let fixture: ComponentFixture<UserProfileValorationDetailComponent>;
        let service: UserProfileValorationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [UserProfileValorationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserProfileValorationService,
                    JhiEventManager
                ]
            }).overrideTemplate(UserProfileValorationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserProfileValorationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserProfileValorationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserProfileValoration(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userProfileValoration).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
