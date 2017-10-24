/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FollowingDetailComponent } from '../../../../../../main/webapp/app/entities/following/following-detail.component';
import { FollowingService } from '../../../../../../main/webapp/app/entities/following/following.service';
import { Following } from '../../../../../../main/webapp/app/entities/following/following.model';

describe('Component Tests', () => {

    describe('Following Management Detail Component', () => {
        let comp: FollowingDetailComponent;
        let fixture: ComponentFixture<FollowingDetailComponent>;
        let service: FollowingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [FollowingDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FollowingService,
                    JhiEventManager
                ]
            }).overrideTemplate(FollowingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FollowingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FollowingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Following(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.following).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
