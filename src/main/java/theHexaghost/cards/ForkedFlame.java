package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jdk.jfr.events.FileReadEvent;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.*;
import theHexaghost.cards.AbstractHexaCard;

public class ForkedFlame extends AbstractHexaCard {

    public final static String ID = makeID("ForkedFlame");

    //stupid intellij stuff SKILL, SELF, COMMON

    public ForkedFlame() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        baseDamage = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        atb(new ExtinguishAction(GhostflameHelper.getPreviousGhostFlame()));
        atb(new ChargeAction(GhostflameHelper.getPreviousGhostFlame()));
        if (upgraded) {
            atb(new ExtinguishCurrentFlameAction());
            atb(new ChargeCurrentFlameAction());
        }
        atb(new ExtinguishAction(GhostflameHelper.getNextGhostFlame()));
        atb(new ChargeAction(GhostflameHelper.getNextGhostFlame()));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeBaseCost(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}