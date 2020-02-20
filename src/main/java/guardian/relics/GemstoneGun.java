package guardian.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import expansioncontent.patches.CenterGridCardSelectScreen;
import guardian.GuardianMod;

import java.util.ArrayList;

public class GemstoneGun extends CustomRelic implements CustomSavable<ArrayList<AbstractCard>> {


    public static final String ID = GuardianMod.makeID("GemstoneGun");

    public static final String IMG_PATH = "relics/GemstoneGun.png";
    public static final String OUTLINE_IMG_PATH = "relics/GemstoneGunOutline.png";
    int numPicked = 0;
    private boolean cardSelected = true;
    private AbstractCard myGemOne = null;
    private AbstractCard myGemTwo = null;
    private AbstractCard myGemThree = null;
    private boolean allDone = false;

    public GemstoneGun() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)), RelicTier.RARE, LandingSound.CLINK);
        allDone = false;
    }

    @Override
    public void onEquip() {
        allDone = false;
        openScreen();
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        myGemOne.use(AbstractDungeon.player, null);
        myGemTwo.use(AbstractDungeon.player, null);
        myGemThree.use(AbstractDungeon.player, null);
    }

    @Override
    public ArrayList<AbstractCard> onSave() {
        ArrayList<AbstractCard> myList = new ArrayList<>();
        myList.add(myGemOne);
        myList.add(myGemTwo);
        myList.add(myGemThree);
        return myList;
    }

    @Override
    public void onLoad(ArrayList<AbstractCard> abstractCards) {
        myGemOne = abstractCards.get(0);
        myGemTwo = abstractCards.get(1);
        myGemThree = abstractCards.get(2);
        setDescriptionAfterLoading();
    }

    private void openScreen() {
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CenterGridCardSelectScreen.centerGridSelect = true;
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        ArrayList<AbstractCard> gems = GuardianMod.getRewardGemCards(false, 3);
        for (AbstractCard q : gems) {
            group.addToTop(q);
        }
        AbstractDungeon.gridSelectScreen.open(group, 1, "Choose a Gem to load the Gem Gun.", false, false, false, false);
    }

    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            this.cardSelected = true;
            CenterGridCardSelectScreen.centerGridSelect = false;

            numPicked++;
            switch (numPicked) {
                case 1:
                    myGemOne = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    break;
                case 2:
                    myGemTwo = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    break;
                case 3:
                    myGemThree = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    break;
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            if (numPicked == 3) {
                allDone = true;
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                description = getUpdatedDescription();
            } else {
                openScreen();
            }
        }
    }

    private void setDescriptionAfterLoading() {
        this.description = DESCRIPTIONS[1] + myGemOne.rawDescription.replaceAll("Gem. NL ", "") + " NL " + myGemTwo.rawDescription.replaceAll("Gem. NL ", "") + " NL " + myGemThree.rawDescription.replaceAll("Gem. NL ", "");
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        if (allDone) {
            return DESCRIPTIONS[1] + myGemOne.rawDescription.replaceAll("Gem. NL ", "") + " NL " + myGemTwo.rawDescription.replaceAll("Gem. NL ", "") + " NL " + myGemThree.rawDescription.replaceAll("Gem. NL ", "");
        }
        return DESCRIPTIONS[0];
    }

}
