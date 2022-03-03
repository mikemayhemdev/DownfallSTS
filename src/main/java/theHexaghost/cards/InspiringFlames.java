package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.ghostflames.AbstractGhostflame;

public class InspiringFlames extends AbstractHexaCard {

    public final static String ID = makeID("InspiringFlames");

    public InspiringFlames() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 7;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
                    if (gf.charged) {
                        att(new UpgradeRandomCardAction());
                    }
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}