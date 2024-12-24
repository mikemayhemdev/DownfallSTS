package expansioncontent.cards;


import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import expansioncontent.expansionContentMod;

import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.forAllMonstersLiving;
import static expansioncontent.expansionContentMod.loadJokeCardImage;

public class YouAreMine extends AbstractExpansionCard {
    public final static String ID = makeID("YouAreMine");

    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 2;
    private static final int downfallMagic = 6;
    private static final int UPGRADE_downfallMagic = 4;

    public YouAreMine() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_collector.png", "expansioncontentResources/images/1024/bg_boss_collector.png");

        tags.add(expansionContentMod.STUDY_COLLECTOR);
        tags.add(expansionContentMod.STUDY);

        baseDownfallMagic = downfallMagic;
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
        loadJokeCardImage(this, "YouAreMine.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((!monster.isDead) && (!monster.isDying) && !monster.halfDead) {
                    atb(new VFXAction(new CollectorCurseEffect(monster.hb.cX, monster.hb.cY), .5F));
                    atb(new ApplyPowerAction(monster, p, new WeakPower(monster, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                    atb(new ApplyPowerAction(monster, p, new VulnerablePower(monster, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                }
            }

                forAllMonstersLiving(q -> applyToEnemy(q, new DoomPower(q, 2*magicNumber)));
            }
        }
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            upgradeDownfall(4);
        }
    }

}