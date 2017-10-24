/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CityDetailComponent } from '../../../../../../main/webapp/app/entities/city/city-detail.component';
import { CityService } from '../../../../../../main/webapp/app/entities/city/city.service';
import { City } from '../../../../../../main/webapp/app/entities/city/city.model';

describe('Component Tests', () => {

    describe('City Management Detail Component', () => {
        let comp: CityDetailComponent;
        let fixture: ComponentFixture<CityDetailComponent>;
        let service: CityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [CityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CityService,
                    JhiEventManager
                ]
            }).overrideTemplate(CityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new City(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.city).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
