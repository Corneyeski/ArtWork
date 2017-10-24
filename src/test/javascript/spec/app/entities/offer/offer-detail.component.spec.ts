/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OfferDetailComponent } from '../../../../../../main/webapp/app/entities/offer/offer-detail.component';
import { OfferService } from '../../../../../../main/webapp/app/entities/offer/offer.service';
import { Offer } from '../../../../../../main/webapp/app/entities/offer/offer.model';

describe('Component Tests', () => {

    describe('Offer Management Detail Component', () => {
        let comp: OfferDetailComponent;
        let fixture: ComponentFixture<OfferDetailComponent>;
        let service: OfferService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [OfferDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OfferService,
                    JhiEventManager
                ]
            }).overrideTemplate(OfferDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OfferDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OfferService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Offer(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.offer).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
