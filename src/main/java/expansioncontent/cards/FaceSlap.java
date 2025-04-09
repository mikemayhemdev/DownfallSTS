package expansioncontent.cards;


import champ.ChampMod;
import champ.relics.PowerArmor;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import downfall.util.CardIgnore;
import expansioncontent.expansionContentMod;
import static expansioncontent.expansionContentMod.loadJokeCardImage;
import java.util.ArrayList;

public class FaceSlap extends AbstractExpansionCard {

    //Super Vicious Mockery
    public final static String ID = makeID("SuperViciousMockery");

    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 3;
    private static final int downfallMagic = 2;
    private static final int UPGRADE_downfallMagic = 1;

    public FaceSlap() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        //todo skill bg instead of power bg
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_champ.png", "expansioncontentResources/images/1024/bg_boss_champ.png");

        tags.add(expansionContentMod.STUDY_CHAMP);
        tags.add(expansionContentMod.STUDY);

        baseDownfallMagic = downfallMagic;
        baseMagicNumber = magicNumber = MAGIC;
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "ViciousMockery.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        vigor(magicNumber);
        applyToEnemy(m, autoWeak(m, downfallMagic));
        atb(new SFXAction("VO_CHAMP_2A"));
        atb(new TalkAction(true, getTaunt(), 2.0F, 2.0F));
    }

    public static void vigor(int begone) {

        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = begone;
                if (AbstractDungeon.player.hasRelic(PowerArmor.ID) && AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {
                    if (x + AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount > PowerArmor.CAP_RESOLVE_ETC) {
                        x = PowerArmor.CAP_RESOLVE_ETC - AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount;
                    }
                }
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, x), x));
            }
        });

    }

    private String getTaunt() {
        ArrayList<String> derp = new ArrayList<>();
        derp.add(Champ.DIALOG[0]);
        derp.add(Champ.DIALOG[1]);
        derp.add(Champ.DIALOG[2]);
        derp.add(Champ.DIALOG[3]);
        return derp.get(MathUtils.random(derp.size() - 1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDownfall(UPGRADE_downfallMagic);
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }

}

