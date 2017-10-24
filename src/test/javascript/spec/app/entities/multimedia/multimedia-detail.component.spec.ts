/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MultimediaDetailComponent } from '../../../../../../main/webapp/app/entities/multimedia/multimedia-detail.component';
import { MultimediaService } from '../../../../../../main/webapp/app/entities/multimedia/multimedia.service';
import { Multimedia } from '../../../../../../main/webapp/app/entities/multimedia/multimedia.model';

describe('Component Tests', () => {

    describe('Multimedia Management Detail Component', () => {
        let comp: MultimediaDetailComponent;
        let fixture: ComponentFixture<MultimediaDetailComponent>;
        let service: MultimediaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [MultimediaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MultimediaService,
                    JhiEventManager
                ]
            }).overrideTemplate(MultimediaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MultimediaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MultimediaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Multimedia(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.multimedia).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
