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

    public ForkedFlame() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 4;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "ForkedFlame.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        atb(new ExtinguishAction(GhostflameHelper.getPreviousGhostFlame()));
        atb(new ChargeAction(GhostflameHelper.getPreviousGhostFlame()));
        atb(new ExtinguishAction(GhostflameHelper.getNextGhostFlame()));
        atb(new ChargeAction(GhostflameHelper.getNextGhostFlame()));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }
}