package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.actions.RetractAction;

public class SpectralAdrenaline extends AbstractHexaCard {

    public final static String ID = makeID("SpectralAdrenaline");

    //bright ritual

    public SpectralAdrenaline() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
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
                if(x == 0){
                    if(GhostflameHelper.activeGhostFlame.charged){
                        atb(new ExtinguishAction(GhostflameHelper.activeGhostFlame));
                        atb(new GainEnergyAction(1));
//                        if (upgraded) {
//                            atb(new DrawCardAction(1));
//                        }
                    }
                }else {
                    int count = 0;
                    for (int i = 0; i <= x; i++) {
                        if (GhostflameHelper.hexaGhostFlames.get(i).charged) {
                            count++;
                        }
                    }
                    atb(new ExtinguishAction(GhostflameHelper.activeGhostFlame));
                    while(GhostflameHelper.hexaGhostFlames.indexOf(GhostflameHelper.activeGhostFlame) != 0){
                        GhostflameHelper.retract();
                    }

                    while (count > 0) {
                        atb(new GainEnergyAction(1));
//                        if (upgraded) {
//                            atb(new DrawCardAction(1));
//                        }
                        count -= 1;
                    }
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}