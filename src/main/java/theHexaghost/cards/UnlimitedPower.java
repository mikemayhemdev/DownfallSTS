package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.TheHexaghost;
import theHexaghost.actions.ChargeAction;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.actions.RetractAction;
import theHexaghost.ghostflames.AbstractGhostflame;

public class UnlimitedPower extends AbstractHexaCard {

    public final static String ID = makeID("UnlimitedPower");

    public UnlimitedPower() {
        super(ID, 4, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "UnlimitedPower.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
            atb(new ExtinguishAction(gf));
            atb(new ChargeAction(gf));
            atb(new WaitAction(0.1F));
        }

        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
//                atb(new ExtinguishAction(GhostflameHelper.hexaGhostFlames.get(5)));
                GhostflameHelper.activeGhostFlame = GhostflameHelper.hexaGhostFlames.get(5);
            }
        });

    }

    //temp crash fix, I don't know how people are still drafting this
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        if (!(AbstractDungeon.player instanceof TheHexaghost)) {
            this.cantUseMessage = "@?!?!@";
            return false;
        }
        return true;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(3);
        }
    }
}
