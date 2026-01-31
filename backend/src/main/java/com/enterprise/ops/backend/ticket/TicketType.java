package com.enterprise.ops.backend.ticket;

/*
 * Defines enterprise ticket category
 */
public enum TicketType {
    DELIVERY,     // Manager → Employee (feature, bug, task)
    ESCALATION    // Employee → Manager (blocker, support, access)
}