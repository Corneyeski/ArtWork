/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PricesTDetailComponent } from '../../../../../../main/webapp/app/entities/prices-t/prices-t-detail.component';
import { PricesTService } from '../../../../../../main/webapp/app/entities/prices-t/prices-t.service';
import { PricesT } from '../../../../../../main/webapp/app/entities/prices-t/prices-t.model';

describe('Component Tests', () => {

    describe('PricesT Management Detail Component', () => {
        let comp: PricesTDetailComponent;
        let fixture: ComponentFixture<PricesTDetailComponent>;
        let service: PricesTService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [PricesTDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PricesTService,
                    JhiEventManager
                ]
            }).overrideTemplate(PricesTDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PricesTDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PricesTService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PricesT(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pricesT).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
