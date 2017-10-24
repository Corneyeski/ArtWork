import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Connection } from './connection.model';
import { ConnectionService } from './connection.service';

@Component({
    selector: 'jhi-connection-detail',
    templateUrl: './connection-detail.component.html'
})
export class ConnectionDetailComponent implements OnInit, OnDestroy {

    connection: Connection;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private connectionService: ConnectionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConnections();
    }

    load(id) {
        this.connectionService.find(id).subscribe((connection) => {
            this.connection = connection;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConnections() {
        this.eventSubscriber = this.eventManager.subscribe(
            'connectionListModification',
            (response) => this.load(this.connection.id)
        );
    }
}
