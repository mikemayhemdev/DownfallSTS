package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.BolsteringGhostflame;

public class EmpoweringStance extends AbstractHexaCard {

    public final static String ID = makeID("EmpoweringStance");

    //stupid intellij stuff POWER, SELF, RARE

    private static final int BLOCK = 6;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public EmpoweringStance() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(HexaMod.GHOSTWHEELCARD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new StrengthPower(p, magicNumber));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
              //  HexaMod.renderFlames = true;
                isDone = true;
                AbstractGhostflame gf = new BolsteringGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                GhostflameHelper.hexaGhostFlames.set(GhostflameHelper.hexaGhostFlames.indexOf(GhostflameHelper.activeGhostFlame), gf);
                gf.activate();
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
            upgradeBlock(3);
        }
    }
}