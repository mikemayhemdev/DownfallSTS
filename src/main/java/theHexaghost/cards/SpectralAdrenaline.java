package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.actions.RetractAction;
import theHexaghost.ghostflames.AbstractGhostflame;

public class SpectralAdrenaline extends AbstractHexaCard {

    public final static String ID = makeID("SpectralAdrenaline");

    //bright ritual

    public SpectralAdrenaline() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
//        this.exhaust = true;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "SpectralAdrenaline.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = GhostflameHelper.hexaGhostFlames.indexOf(GhostflameHelper.activeGhostFlame);
                addToBot(new RetractAction(x));
                for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
                    if (gf.charged) {
                        atb(new ExtinguishAction(gf));
                        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
                    }
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}